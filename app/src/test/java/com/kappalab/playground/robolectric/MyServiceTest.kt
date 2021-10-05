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
        app.startService(Intent(app, MyService::class.java))
        ShadowLooper.shadowMainLooper().idle()
        Assert.assertTrue(MyService.started)
    }
}
