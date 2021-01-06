package com.kappalab.playground.robolectric

import android.app.Application
import android.bluetooth.*
import android.content.*
import android.provider.Settings
import android.provider.Settings.System
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log


interface Notifier {
    val status: String
}

class BluetoothNotifier(application: Application, callback: (String) -> Unit) : Notifier {
    override var status: String = ""
        private set

    init {
        val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        val profileListener = object : BluetoothProfile.ServiceListener {
            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile) {
                Log.d("BluetoothNotifier", "onServiceConnected")
                if (profile != BluetoothProfile.HEADSET) return
                status = "Connected"
                callback.invoke(status)
            }

            override fun onServiceDisconnected(profile: Int) {
                Log.d("BluetoothNotifier", "onServiceDisconnected")
                if (profile != BluetoothProfile.HEADSET) return
                status = "Disconnected"
                callback.invoke(status)
            }
        }

        bluetoothAdapter.getProfileProxy(
            application,
            profileListener,
            BluetoothProfile.HEADSET
        )
    }
}

class TelephoneNotifier(application: Application, callback: (String) -> Unit) : Notifier {
    override var status: String = ""
        private set

    init {
        val receiver = object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String?) {
                status = when (state) {
                    TelephonyManager.CALL_STATE_IDLE -> "CALL_STATE_IDLE"
                    TelephonyManager.CALL_STATE_RINGING -> "CALL_STATE_RINGING"
                    TelephonyManager.CALL_STATE_OFFHOOK -> "CALL_STATE_OFFHOOK"
                    else -> "unknown"
                }
                Log.d("TelephoneNotifier", "$state, $incomingNumber")
                callback.invoke(status)
            }
        }

        val tm = application.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        tm.listen(receiver, PhoneStateListener.LISTEN_CALL_STATE)
    }
}

class AirPlaneModeNotifier(application: Application, callback: (String) -> Unit) : Notifier {
    override var status: String = ""
        private set

    init {
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("AirPlaneModeNotifier", "${intent?.extras}")


                context?.let{
                    val airplaneMode = System.getInt(
                        it.contentResolver,
                        Settings.Global.AIRPLANE_MODE_ON,
                        0
                    ) != 0

                    status = if(airplaneMode) "ON" else "OFF"
                    callback.invoke(status)
                }
            }
        }

        application.registerReceiver(receiver, IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED))
    }
}