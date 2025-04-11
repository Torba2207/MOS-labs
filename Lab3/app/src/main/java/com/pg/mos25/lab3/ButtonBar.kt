package com.pg.mos25.lab3

import android.content.Intent
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pg.fileeditor.FileManager
import com.pg.fileeditor.HistoryManager

import com.pg.mos25.lab3.ui.theme.ButtonBarBackground
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@Composable
fun ButtonBar(
    title:String,
    text:String,
    onTitleChange:(String)->Unit,
    onTextChange:(String)->Unit,
    fileManager: FileManager
){
    val context= LocalContext.current
    val fileName = "$title.txt"

    val downloadDirPath =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
    val file = File(downloadDirPath, fileName)
    val historyManager = remember { HistoryManager() }
    val openFileLauncher = fileManager.registerOpenFileLauncher { uri ->
        if (uri != null) {
            val content = fileManager.readTextFromUri(context, uri)
            val fileName = fileManager.getFileName(context, uri) ?: ""
            onTitleChange(fileName.substringBeforeLast("."))
            onTextChange(content)
            historyManager.updateLastOpenedFile(uri, fileManager, context)
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ButtonBarBackground),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BottomBarButton(
            iconVector = Icons.Default.Check,
            onClickEvent = {
                try {
                    FileOutputStream(file).use { fileOutputStream ->
                        fileOutputStream.write(text.toByteArray())
                        Log.d("saveFile", "File saved successfully: $file")
                        Toast.makeText(
                            context,
                            "File successfully saved with name: $fileName",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: IOException) {
                    Log.e("saveFile", "Error saving file: ${e.message}")
                }
            },
            name = "Save"
        )
        BottomBarButton(
            iconVector = Icons.Default.Edit,
            onClickEvent = {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    addCategory(Intent.CATEGORY_OPENABLE)
                    type = "text/plain"
                    putExtra(DocumentsContract.EXTRA_INITIAL_URI, Environment.DIRECTORY_DOWNLOADS)
                }
                openFileLauncher.launch(intent)
            },
            name = "Open"
        )

        BottomBarButton(
            iconVector = Icons.Default.Refresh,
            onClickEvent = { historyManager.showHistory(context) },
            name = "History"
        )

    }
}
