package com.kappalab.playground.robolectric

import android.app.Application
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowBluetoothAdapter
import org.robolectric.shadows.ShadowLooper


@RunWith(RobolectricTestRunner::class)
class NotifierTest {
    @Test
    fun testAirPlaneModeNotifier() {
        val app = ApplicationProvider.getApplicationContext<Application>()

        var received = ""
        AirPlaneModeNotifier(app) { received = it }

        app.sendBroadcast(Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED))
        ShadowLooper.shadowMainLooper().idle()
        assertEquals("OFF", received)
    }

    @Test
    fun testTelephoneNotifier() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val tmShadow = shadowOf(app.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager)

        var received = ""
        TelephoneNotifier(app) { received = it }

        tmShadow.setCallState(TelephonyManager.CALL_STATE_RINGING)
        ShadowLooper.shadowMainLooper().idle()
        assertEquals("CALL_STATE_RINGING", received)

        tmShadow.setCallState(TelephonyManager.CALL_STATE_OFFHOOK)
        ShadowLooper.shadowMainLooper().idle()
        assertEquals("CALL_STATE_OFFHOOK", received)
    }

    @Test
    fun testBluetoothNotifier() {
        val app = ApplicationProvider.getApplicationContext<Application>()
        val shadowAdapter: ShadowBluetoothAdapter = shadowOf(BluetoothAdapter.getDefaultAdapter())

        val proxy = object : BluetoothProfile {
            override fun getConnectedDevices(): MutableList<BluetoothDevice> {
                TODO("Not yet implemented")
            }

            override fun getDevicesMatchingConnectionStates(p0: IntArray?): MutableList<BluetoothDevice> {
                TODO("Not yet implemented")
            }

            override fun getConnectionState(p0: BluetoothDevice?): Int {
                TODO("Not yet implemented")
            }
        }

        shadowAdapter.setProfileProxy(BluetoothProfile.HEADSET, proxy)

        var received = ""
        BluetoothNotifier(app) { received = it }

        //ShadowBluetoothAdapter will immediately call onServiceConnected
        assertEquals(received, "Connected")
    }
}

