package com.pg.mos25.lab4

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.pg.mos25.lab4.ui.theme.Lab4Theme

class MainActivity : ComponentActivity(), MyService.ServiceCallback {
    private lateinit var service: MyService
    private var bound by mutableStateOf(false)
    private var elapsedTime by mutableStateOf(0)
    private var isServiceRunning by mutableStateOf(false)

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
            service = (binder as MyService.LocalBinder).getService()
            bound = true
            service.obtainCallback(this@MainActivity)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            bound = false
        }
    }

    override fun onTimeUpdated(seconds: Int) {
        elapsedTime = seconds
    }
/*
    override fun onStart() {
        super.onStart()
        Intent(this, MyService::class.java).also {
            startService(it)
            bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            service.obtainCallback(null) // Clean up reference
            unbindService(serviceConnection)
            bound = false
        }
    }
*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()

        setContent {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Service Running Time:", fontSize = 20.sp)
                    Text(text = "$elapsedTime seconds", fontSize = 32.sp, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(onClick = {
                        if (isServiceRunning) {
                            stopAndUnbindService()
                        } else {
                            startAndBindService()
                        }
                        isServiceRunning = !isServiceRunning
                    }) {
                        Text(if (isServiceRunning) "Stop Service" else "Start Service")
                    }
                }
            }
        }
    }
    private fun startAndBindService() {
        Intent(this, MyService::class.java).also {
            startService(it)
            bindService(it, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    private fun stopAndUnbindService() {
        if (bound) {
            service.obtainCallback(null)
            unbindService(serviceConnection)
            bound = false
        }
        stopService(Intent(this, MyService::class.java))
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(POST_NOTIFICATIONS), 0)
        }
    }
}
