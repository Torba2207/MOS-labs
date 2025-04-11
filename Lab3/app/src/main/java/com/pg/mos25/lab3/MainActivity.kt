package com.pg.mos25.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pg.fileeditor.FileManager
import com.pg.mos25.lab3.ui.theme.Lab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NoteActivity(modifier = Modifier.padding(innerPadding));
                }
            }
        }
    }
}


@Composable
fun NoteActivity(modifier: Modifier=Modifier){
    var text by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    val fileManager = remember { FileManager() }
    Column(
        modifier = Modifier
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text("Title")
        EditableTextField(text=title, onValueChange = {title=it})
        Text("Content")
        EditableTextField(text = text, onValueChange = {text=it})
        Spacer(modifier = Modifier.weight(1f))
        ButtonBar(text = text, title = title, onTextChange = {text=it}, onTitleChange = {title=it},
            fileManager = fileManager)
    }
}

@Preview(showBackground = true)
@Composable
fun NotePreview(){
    Lab3Theme {
        NoteActivity()
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
    Lab3Theme {
        Greeting("Android")
    }
}