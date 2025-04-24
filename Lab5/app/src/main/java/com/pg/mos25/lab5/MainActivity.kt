package com.pg.mos25.lab5

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pg.mos25.lab5.R

class MainActivity : AppCompatActivity() {

    private lateinit var generatedArray: IntArray

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
            val arrayCopy = generatedArray.copyOf()

            val startKotlin = System.nanoTime()
            val kotlinSorted = sortArrayKotlin(arrayCopy)
            val endKotlin = System.nanoTime()
            val arrText=arrayCopy.joinToString(", ")
            val startCpp = System.nanoTime()
            sortArray(arrayCopy)
            val endCpp = System.nanoTime()

            sortedText.text = arrayCopy.joinToString(", ")

            val durationCppMs = (endCpp - startCpp) / 1_000_000.0
            val durationKotlinMs = (endKotlin - startKotlin) / 1_000_000.0
            val performanceText = """
                C++ sort time: ${"%.3f".format(durationCppMs)} ms
                Kotlin sort time: ${"%.3f".format(durationKotlinMs)} ms
            """.trimIndent()

            findViewById<TextView>(R.id.txtPerformance).text = performanceText
        }
    }
}
