package com.example.android.politicalpreparedness

import android.app.Application
import android.content.Context

class PoliticalApp : Application() {
    companion object {
        private lateinit var instance: Application
        val context: Context get() = instance.applicationContext
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}