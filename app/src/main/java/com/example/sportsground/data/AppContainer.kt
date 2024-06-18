package com.example.sportsground.data

import android.content.Context
import com.example.sportsground.data.repository.GroundRepository
import com.example.sportsground.data.repository.GroundRepositoryImpl

interface AppContainer {
    val groundList: GroundRepository
}

class AppContainerImpl(private val applicationContext: Context) : AppContainer {
    override val groundList: GroundRepository by lazy {
        GroundRepositoryImpl()
    }
}