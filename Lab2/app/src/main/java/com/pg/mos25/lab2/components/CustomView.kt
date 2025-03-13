package com.pg.mos25.lab2.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomView : View {
    private var red=false
    private var rectangle=false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) :
            super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle:
    Int) : super(context, attrs, defStyle)




    fun setColor(isRed: Boolean) {
        red = isRed
    }

    fun setShape(isRectangle: Boolean) {
        rectangle = isRectangle
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val paint = Paint().apply {
            color = if (red) Color.RED else Color.BLUE
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        if (rectangle) {
            canvas.drawRect(100f, 100f, 300f, 200f, paint)
        } else {
            canvas.drawCircle(200f, 200f, 100f, paint)
        }


    }
}
