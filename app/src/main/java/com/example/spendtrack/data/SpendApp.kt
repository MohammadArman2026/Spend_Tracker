package com.example.spendtrack.data

import android.app.Application

class SpendApp: Application() {
    override fun onCreate() {
        super.onCreate()
        SpendGraph.provide(this)
    }
}

