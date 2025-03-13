package com.pg.mos25.lab2.components

import android.content.Context
import android.content.Intent
import android.graphics.pdf.content.PdfPageGotoLinkContent.Destination
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Outline

@Composable
fun ProceedButton(name: String, context: Context,
                  destination: Class<*>,
                  isRed:Boolean=false,
                  isRectangle: Boolean=false){
    Button(onClick = {
        val intent=Intent(context,destination).apply {
            putExtra("EXTRA_RED", isRed)
            putExtra("EXTRA_RECT", isRectangle)
        }
        context.startActivity(intent)
    }){
        Text(name)
    }
}