package com.kappalab.playground.robolectric

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "Init")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BluetoothNotifier(application){
            Log.i("MainActivity", "callback Bluetooth $it")
        }

        TelephoneNotifier(application){
            Log.i("MainActivity", "callback Telephone $it")
        }

        AirPlaneModeNotifier(application){
            Log.i("MainActivity", "callback AirPlane $it")
        }

    }
}