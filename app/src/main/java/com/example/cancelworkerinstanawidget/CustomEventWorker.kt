package com.example.cancelworkerinstanawidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.RemoteViews
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.instana.android.CustomEvent
import com.instana.android.Instana
import com.instana.android.core.event.worker.EventWorker
import java.util.UUID
import java.util.concurrent.TimeUnit

class CustomWidget : AppWidgetProvider() {

    companion object {
        private const val TAG = "CustomWidget"
        private const val PREFS_NAME = "widget_prefs"
        private const val PREF_STATE = "state"
        private const val PREF_USER_ID = "user_id" // Campo para almacenar User ID
        private const val PREF_USER_CEDULA = "user_cedula" // Campo para almacenar cédula
        private const val STATE_HIDDEN = "hidden"
        private const val STATE_VISIBLE = "visible"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        Log.d(TAG, "onUpdate: Actualizando widgets: ${appWidgetIds.contentToString()}")

        for (appWidgetId in appWidgetIds) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

            // Generar nuevos datos de usuario automáticamente en cada actualización
            val userId = generateAndStoreUserId(prefs)
            val userCedula = generateAndStoreUserCedula(prefs)

            val currentState = prefs.getString(PREF_STATE, STATE_HIDDEN) ?: STATE_HIDDEN

            val views = RemoteViews(context.packageName, R.layout.widget_layout).apply {
                setTextViewText(R.id.textLabel, if (currentState == STATE_VISIBLE) userCedula else "*****")
            }

            val intentToggle = Intent(context, CustomWidget::class.java).apply {
                action = "ACTION_TOGGLE"
            }
            val pendingIntentToggle = PendingIntent.getBroadcast(
                context, 0, intentToggle, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.buttonToggle, pendingIntentToggle)

            val intentKill = Intent(context, CustomWidget::class.java).apply {
                action = "ACTION_KILL"
            }
            val pendingIntentKill = PendingIntent.getBroadcast(
                context, 1, intentKill, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.buttonKill, pendingIntentKill)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

        val action = intent.action
        Log.d(TAG, "onReceive: Acción recibida: $action")

        val appWidgetManager = AppWidgetManager.getInstance(context)
        val component = ComponentName(context, CustomWidget::class.java)
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Generar o recuperar el User ID y cédula únicos
        val userId = generateAndStoreUserId(prefs)
        val userCedula = generateAndStoreUserCedula(prefs)

        when (action) {
            "ACTION_TOGGLE" -> {
                val currentState = prefs.getString(PREF_STATE, STATE_HIDDEN) ?: STATE_HIDDEN
                val newState = if (currentState == STATE_HIDDEN) STATE_VISIBLE else STATE_HIDDEN

                prefs.edit().putString(PREF_STATE, newState).apply()

                val views = RemoteViews(context.packageName, R.layout.widget_layout).apply {
                    setTextViewText(R.id.textLabel, if (newState == STATE_VISIBLE) userCedula else "*****")
                }

                appWidgetManager.partiallyUpdateAppWidget(appWidgetManager.getAppWidgetIds(component), views)

                val event = CustomEvent("Toggle Button Pressed")
                event.meta = mapOf("State" to newState, "User.ID" to userId, "User.Cedula" to userCedula)
                Instana.reportEvent(event)

                Log.d(TAG, "onReceive: Estado actualizado a $newState y reportado a Instana con User.ID: $userId y Cédula: $userCedula")
            }

            "ACTION_KILL" -> {
                WorkManager.getInstance(context).cancelAllWork()
                enqueueDelayedWorker(context)

                val event = CustomEvent("Kill Button Pressed")
                event.meta = mapOf("User.ID" to userId, "User.Cedula" to userCedula)
                Instana.reportEvent(event)

                Log.d(TAG, "onReceive: Tareas canceladas y evento reportado a Instana con User.ID: $userId y Cédula: $userCedula")
            }

            else -> Log.d(TAG, "onReceive: Acción desconocida: $action")
        }
    }

    private fun generateAndStoreUserId(prefs: SharedPreferences): String {
        val newUserId = UUID.randomUUID().toString()
        prefs.edit().putString(PREF_USER_ID, newUserId).apply()
        Log.d(TAG, "generateAndStoreUserId: Generado nuevo User.ID: $newUserId")
        return newUserId
    }

    private fun generateAndStoreUserCedula(prefs: SharedPreferences): String {
        val newCedula = (100000 + (Math.random() * 900000)).toInt().toString() // Generar número aleatorio de 6 dígitos
        prefs.edit().putString(PREF_USER_CEDULA, newCedula).apply()
        Log.d(TAG, "generateAndStoreUserCedula: Generada nueva Cédula: $newCedula")
        return newCedula
    }

    private fun enqueueDelayedWorker(context: Context) {
        val workRequest = OneTimeWorkRequest.Builder(EventWorker::class.java)
            .setInitialDelay(5 * 365, TimeUnit.DAYS)
            .setConstraints(Constraints.Builder().setRequiresCharging(true).build())
            .build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "appWidgetWorkerKeepEnabled",
            ExistingWorkPolicy.KEEP,
            workRequest
        )
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        Log.d(TAG, "onEnabled: Widget habilitado.")
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        Log.d(TAG, "onDisabled: Widget deshabilitado.")
    }
}
