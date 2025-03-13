package com.pg.mos25.lab2.components

import android.content.Context
import android.content.Intent
import android.graphics.pdf.content.PdfPageGotoLinkContent.Destination
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.Modifier

@Composable
fun ProceedButton(name: String, context: Context, destination: Class<*>, modifier: Modifier=Modifier){
    Button(onClick = {
        val intent=Intent(context,destination)
        context.startActivity(intent)
    }){
        Text(name)
    }
}