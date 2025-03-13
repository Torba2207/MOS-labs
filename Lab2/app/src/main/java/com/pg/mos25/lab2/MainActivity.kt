package com.pg.mos25.lab2

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.IntentCompat
import com.pg.mos25.lab2.components.ProceedButton
import com.pg.mos25.lab2.ui.theme.Lab2Theme

class MainActivity : ComponentActivity() {
    private lateinit var captureImageView: ImageView
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts
        .StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.let {
                val thumbnail: Bitmap? = IntentCompat
                    .getParcelableExtra(data, "data", Bitmap::class.java)
                thumbnail?.let {
                    captureImageView.setImageBitmap(thumbnail)
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab2Theme {
                val context= LocalContext.current
                val isRed = remember { mutableStateOf(false) }
                val isRectangle = remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Change Shape and Color", fontSize = 20.sp)

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Red Color")
                            Switch(
                                checked = isRed.value,
                                onCheckedChange = { checked -> isRed.value = checked }
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Rectangle Shape")
                            Switch(
                                checked = isRectangle.value,
                                onCheckedChange = { checked -> isRectangle.value = checked }
                            )
                        }


                        ProceedButton(
                            "To the Second Activity", context,
                            SecondActivity::class.java,
                            isRed = isRed.value,
                            isRectangle = isRectangle.value
                        )


                        // Button to launch the camera
                        Button(onClick = { launchCamera() }) {
                            Text("Open Camera")
                        }





                    }
                    //Spacer(modifier = Modifier.weight(1f))
                    // ImageView to display the captured image
                    AndroidView(
                        factory = { ctx ->
                            ImageView(ctx).apply {
                                captureImageView = this
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    600
                                )
                                scaleType = ImageView.ScaleType.CENTER_CROP
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            //.align(Alignment.BottomCenter)
                            .offset(y=400.dp)
                    )
                }
            }
        }
    }
    private fun launchCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
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