package com.pg.mos25.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.pg.mos25.lab2.components.CustomView
import com.pg.mos25.lab2.ui.theme.Lab2Theme

class SecondActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val isRed = intent.getBooleanExtra("EXTRA_RED", false)
        val isRectangle = intent.getBooleanExtra("EXTRA_RECT", false)
        setContent{
            Lab2Theme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ){
                    AndroidView(
                        factory = {context->CustomView(context)},
                        update = {
                            view ->
                            view.setColor(isRed)
                            view.setShape(isRectangle)
                            view.invalidate()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }
            }
        }
    }
}

