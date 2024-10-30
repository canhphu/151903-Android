package com.example.bai1

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.*
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtInputNumber: EditText = findViewById(R.id.edtInputNumber)
        val radioEven: RadioButton = findViewById(R.id.radioEven)
        val radioOdd: RadioButton = findViewById(R.id.radioOdd)
        val radioSquare: RadioButton = findViewById(R.id.radioSquare)
        val btnShow: Button = findViewById(R.id.btnShow)
        val listView: ListView = findViewById(R.id.listView)
        val tvError: TextView = findViewById(R.id.tvError)

        btnShow.setOnClickListener {
            val input = edtInputNumber.text.toString()
            if (input.isEmpty()) {
                tvError.text = "Vui lòng nhập một số."
                tvError.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val n = input.toIntOrNull()
            if (n == null || n <= 0) {
                tvError.text = "Số nhập vào không hợp lệ."
                tvError.visibility = View.VISIBLE
                return@setOnClickListener
            }
            tvError.visibility = View.GONE

            val resultList = when {
                radioEven.isChecked -> (0..n).filter { it % 2 == 0 }
                radioOdd.isChecked -> (1..n).filter { it % 2 != 0 }
                radioSquare.isChecked -> (0..n).filter { isPerfectSquare(it) }
                else -> emptyList()
            }

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, resultList)
            listView.adapter = adapter
        }
    }

    private fun isPerfectSquare(number: Int): Boolean {
        val root = sqrt(number.toDouble()).toInt()
        return root * root == number
    }
}
