package com.example.sportsground

import android.app.Application
import com.example.sportsground.data.AppContainer
import com.example.sportsground.data.AppContainerImpl

class GroundApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }
}