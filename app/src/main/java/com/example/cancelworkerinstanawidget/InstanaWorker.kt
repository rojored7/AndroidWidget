package com.example.cancelworkerinstanawidget

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import android.util.Log

class InstanaWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        try {
            // Lógica para enviar eventos a Instana
            Log.d("InstanaWorker", "Worker ejecutado correctamente.")
            return Result.success()
        } catch (e: Exception) {
            Log.e("InstanaWorker", "Error en el Worker: ${e.message}")
            return Result.failure()
        }
    }
}
