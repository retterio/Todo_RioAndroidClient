package com.retter.rettersdk

import android.app.Application
import com.rettermobile.rio.Rio
import com.rettermobile.rio.service.RioNetworkConfig

class App : Application() {
    companion object {
        lateinit var rio: Rio
    }

    override fun onCreate() {
        super.onCreate()
        rio = Rio(
            applicationContext = applicationContext,
            projectId = "2g8eht73b",
            culture= "en",
            config = RioNetworkConfig.build {
                customDomain = "api.a101kapida.com"
            }
        )
    }
}