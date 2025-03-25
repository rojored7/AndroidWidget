package com.example.cancelworkerinstanawidget

import android.app.Application
import com.instana.android.Instana
import com.instana.android.core.InstanaConfig

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Configuración de Instana
        val instanaConfig = InstanaConfig(
            reportingURL = "https://eum-red-saas.instana.io/mobile",
            key = "pr1LLGaDRC-7D4mrcjzPoQ",
            enableCrashReporting = true,
            autoCaptureScreenNames = true
        )
        Instana.setup(this, instanaConfig)
    }
}


