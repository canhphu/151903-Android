package com.example.currency

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val currencies = arrayOf("USD", "EUR", "GBP", "VND", "JPY") // Updated currency options
    private val rates = arrayOf(1.0, 0.85, 0.75, 23000.0, 110.0) // Updated conversion rates for USD

    private lateinit var editText: EditText
    private lateinit var editText2: EditText
    private lateinit var fromSpinner: Spinner
    private lateinit var toSpinner: Spinner
    private var isEditText1Updating = false
    private var isEditText2Updating = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        editText = findViewById(R.id.editText)
        editText2 = findViewById(R.id.editText2)
        fromSpinner = findViewById(R.id.spinner1)
        toSpinner = findViewById(R.id.spinner2)

        // Setting up spinners
        setupSpinners()

        // Adding TextWatcher for bidirectional conversion
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isEditText2Updating) {
                    isEditText1Updating = true
                    convertCurrency(editText, editText2, fromSpinner.selectedItemPosition, toSpinner.selectedItemPosition)
                    isEditText1Updating = false
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        editText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!isEditText1Updating) {
                    isEditText2Updating = true
                    convertCurrency(editText2, editText, toSpinner.selectedItemPosition, fromSpinner.selectedItemPosition)
                    isEditText2Updating = false
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupSpinners() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fromSpinner.adapter = adapter
        toSpinner.adapter = adapter

        fromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                if (!isEditText1Updating) {
                    convertCurrency(editText, editText2, fromSpinner.selectedItemPosition, toSpinner.selectedItemPosition)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        toSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                if (!isEditText2Updating) {
                    convertCurrency(editText, editText2, fromSpinner.selectedItemPosition, toSpinner.selectedItemPosition)
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        fromSpinner.setSelection(0)
        toSpinner.setSelection(1)
    }

    private fun convertCurrency(inputEditText: EditText, outputEditText: EditText, fromCurrencyIndex: Int, toCurrencyIndex: Int) {
        val amountStr = inputEditText.text.toString()
        if (amountStr.isEmpty()) {
            outputEditText.text.clear()
            return
        }

        val amount = amountStr.toDoubleOrNull() ?: return
        val convertedAmount = amount * rates[toCurrencyIndex] / rates[fromCurrencyIndex]
        outputEditText.setText(String.format("%.2f", convertedAmount))
    }
}
