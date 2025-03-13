package com.pg.mos25.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pg.mos25.lab2.components.ProceedButton
import com.pg.mos25.lab2.ui.theme.Lab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab2Theme {
                val isRed = remember { mutableStateOf(false) }
                val isRectangle = remember { mutableStateOf(false) }
                Column (modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Change Shape and Color", fontSize = 20.sp)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Red Color")
                        Switch(
                            checked = isRed.value,
                            onCheckedChange = { checked -> isRed.value=checked }
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Rectangle Shape")
                        Switch(
                            checked = isRectangle.value,
                            onCheckedChange = { checked -> isRectangle.value=checked }
                        )
                    }


                    ProceedButton("To the Second Activity", LocalContext.current,
                        SecondActivity::class.java,
                        isRed=isRed.value,
                        isRectangle=isRectangle.value)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab2Theme {
        Greeting("Android")
    }
}