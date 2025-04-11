package com.pg.mos25.lab4

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : LifecycleService() {
    private val binder = LocalBinder()
    private var startTime = 0L
    private var callback: ServiceCallback? = null
    interface ServiceCallback {
        fun onTimeUpdated(seconds: Int)
    }
    inner class LocalBinder : Binder() {
        fun getService(): MyService = this@MyService
    }

    fun obtainCallback(callback: ServiceCallback?) {
        this.callback = callback
    }


    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        startTime = System.currentTimeMillis()

        lifecycleScope.launch {
            while (true) {
                delay(1000)
                val seconds = ((System.currentTimeMillis() - startTime) / 1000).toInt()
                callback?.onTimeUpdated(seconds)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        callback = null
    }


    private fun startForegroundService() {
        val channelId = "timer_service_channel"
        val channel = NotificationChannel(channelId, "Timer Service", NotificationManager.IMPORTANCE_DEFAULT)
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Timer Service")
            .setContentText("Counting time...")
            .setSmallIcon(android.R.drawable.ic_popup_sync)
            .build()

        ServiceCompat.startForeground(
            this, 1, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
        )
    }
}