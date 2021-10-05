package com.kappalab.playground.robolectric

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "Init")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BluetoothNotifier(application) {
            Log.i(TAG, "callback Bluetooth $it")
        }

        TelephoneNotifier(application) {
            Log.i(TAG, "callback Telephone $it")
        }

        AirPlaneModeNotifier(application) {
            Log.i(TAG, "callback AirPlane $it")
        }


        startService(Intent(this@MainActivity, MyService::class.java))
    }
}
