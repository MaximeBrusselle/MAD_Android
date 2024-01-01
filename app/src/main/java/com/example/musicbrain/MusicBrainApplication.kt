package com.example.musicbrain

import android.app.Application
import com.example.musicbrain.data.AppContainer
import com.example.musicbrain.data.DefaultAppContainer

/**
 * Application class for the MusicBrain application.
 */
class MusicBrainApplication : Application() {
    /**
     * The [AppContainer] instance for the application.
     */
    lateinit var container: AppContainer

    /**
     * Called when the application is starting, before any other application objects have been created.
     */
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}
