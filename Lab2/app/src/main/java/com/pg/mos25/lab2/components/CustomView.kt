package com.pg.mos25.lab2.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) :
            super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle:
    Int) : super(context, attrs, defStyle)


    private var red=false
    private var rectangle=false

    fun setColor(){
        red=!red
    }

    fun setShape(){
        rectangle=!rectangle
    }




    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        // Draw a red circle

        val centerX = width / 3f
        val centerY = height / 3f
        val radius = 100f

        // Draw a blue rectangle
        val left = width / 2f
        val top = height / 2f
        val right = left + 200f
        val bottom = top + 100f
        when{
            red&&rectangle->canvas.drawRect(left, top, right, bottom, Paint().apply {
                color =Color.RED // Set color
                style = Paint.Style.FILL // Fill the shape
                isAntiAlias = true // Smooth edges
            })
            red&&!rectangle->canvas.drawCircle(centerX, centerY, radius, Paint().apply {
                color = Color.RED
                style = Paint.Style.FILL
                isAntiAlias = true
            })

            !red&&rectangle->canvas.drawRect(left, top, right, bottom, Paint().apply {
                color =Color.BLUE
                style = Paint.Style.FILL
                isAntiAlias = true
            })

            !red&&!rectangle->canvas.drawCircle(centerX, centerY, radius, Paint().apply {
                color = Color.BLUE
                style = Paint.Style.FILL
                isAntiAlias = true
            })

        }


    }
}
