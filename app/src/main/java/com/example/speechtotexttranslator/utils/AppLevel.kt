package com.example.speechtotexttranslator.utils

import android.app.Application
import android.os.StrictMode
import com.example.speechtotexttranslator.db.MyRoom
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics

class AppLevel : Application() {

    val room: MyRoom by lazy { MyRoom.getInstance(this) }
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
}