package com.example.musicbrain

import android.app.Application
import com.example.musicbrain.data.AppContainer
import com.example.musicbrain.data.DefaultAppContainer

class MusicBrainApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(context = applicationContext)
    }
}
