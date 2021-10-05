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

    override fun onBind(intent: Intent): IBinder? {
        Log.i(TAG, "onBind: $intent")
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand: $intent")
        started = true
        return super.onStartCommand(intent, flags, startId)
    }
}
