package com.example.cancelworkerinstanawidget

import android.annotation.SuppressLint
import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class InstanaForegroundService : Service() {

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, "instana_channel")
            .setContentTitle("Instana Running")
            .setContentText("Critical Instana task in progress")
            .setSmallIcon(android.R.drawable.ic_menu_info_details) // Cambia este ícono por uno en tu proyecto
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        startForeground(1, notification)

        // Aquí ejecuta tareas críticas si es necesario
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
