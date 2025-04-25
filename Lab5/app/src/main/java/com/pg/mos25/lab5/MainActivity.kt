package com.pg.mos25.lab5

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pg.mos25.lab5.R

class MainActivity : AppCompatActivity() {

    private lateinit var generatedArray: IntArray
    private var kotlinTime: Float = 0.0F
    private var cppTime: Float=0.0F

    private external fun sortArray(array: IntArray)

    init {
        System.loadLibrary("lab5")
    }

    fun sortArrayKotlin(array: IntArray): IntArray {
        return array.sortedArray()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val generateButton = findViewById<Button>(R.id.btnGenerate)
        val sortButton = findViewById<Button>(R.id.btnSort)
        val arrayText = findViewById<TextView>(R.id.txtArray)
        val sortedText = findViewById<TextView>(R.id.txtSorted)

        generateButton.setOnClickListener {
            generatedArray = IntArray(100) { (0..999).random() }
            arrayText.text = generatedArray.joinToString(", ")
            sortedText.text = ""
        }

        sortButton.setOnClickListener {

            for(i in 0..10) {
                val arrayCopy = generatedArray.copyOf()
                val startKotlin = System.nanoTime()
                val kotlinSorted = sortArrayKotlin(arrayCopy)
                val endKotlin = System.nanoTime()
                val arrText = arrayCopy.joinToString(", ")
                val startCpp = System.nanoTime()
                sortArray(arrayCopy)
                val endCpp = System.nanoTime()
                if(i==0)
                    sortedText.text = arrayCopy.joinToString(", ")
                val durationCppMs = ((endCpp - startCpp) / 1_000_000.0).toFloat()
                val durationKotlinMs = ((endKotlin - startKotlin) / 1_000_000.0).toFloat()
                kotlinTime+=durationKotlinMs
                cppTime+=durationCppMs
            }


            val performanceText = """
                C++ sort time: ${"%.3f".format(cppTime/10)} ms
                Kotlin sort time: ${"%.3f".format(kotlinTime/10)} ms
            """.trimIndent()
            cppTime=0.0F
            kotlinTime=0.0F

            findViewById<TextView>(R.id.txtPerformance).text = performanceText
        }
    }
}
