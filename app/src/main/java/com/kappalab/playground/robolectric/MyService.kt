package com.kappalab.playground.robolectric

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {

    companion object {
        private const val TAG = "MyService"

        var started: Boolean = false
            private set
    }

    private var count = 0

    init {
        Log.i(TAG, "init")
    }

    override fun onCreate() {
        Log.i(TAG, "onCreate")
        super.onCreate()
    }

    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "onBind: $intent")
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand: $intent, count:$count")
        count++
        started = true
        return super.onStartCommand(intent, flags, startId)
    }
}
