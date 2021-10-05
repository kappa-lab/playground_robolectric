package com.kappalab.playground.robolectric

import android.app.Application
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.shadows.ShadowLooper

@RunWith(RobolectricTestRunner::class)
class MyServiceTest {
    @Test
    fun testStarted() {
        val app = ApplicationProvider.getApplicationContext<Application>()

        Assert.assertFalse(MyService.started)

//        Cant not receive onStartCommand
//        app.startService(Intent(app, MyService::class.java))
//        ShadowLooper.shadowMainLooper().idle()

        MyService().onStartCommand(Intent(app, MyService::class.java),0,0)
        Assert.assertTrue(MyService.started)

    }
}
