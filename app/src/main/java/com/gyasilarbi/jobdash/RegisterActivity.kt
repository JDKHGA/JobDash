package com.gyasilarbi.jobdash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.app.DatePickerDialog
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import java.util.*
import com.google.android.material.textfield.TextInputEditText


class RegisterActivity : AppCompatActivity() {
    // Declaring variables
    lateinit var selectedDateEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

//        val items = listOf("Accra", "Kumasi", "Cape Coast", "Takoradi", "Koforidua", "Ho", "Tamale ")
//
//        val autoComplete: AutoCompleteTextView = findViewById(R.id.autoComplete)
//
//        val adapter = ArrayAdapter(this,R.layout.list_item,items)
//
//        autoComplete.setAdapter(adapter)
//
//        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
//                adapterView, view, i, l ->
//
//            val itemSelected = adapterView.getItemAtPosition(i)
//
//            Toast.makeText(this, "Item: $itemSelected", Toast.LENGTH_SHORT).show()
//
//        }

        // Initializing variables
        selectedDateEditText = findViewById(R.id.idTVSelectedDate)

        // Adding click listener to the TextInputEditText
        selectedDateEditText.setOnClickListener {
            // Getting instance of the calendar
            val c = Calendar.getInstance()

            // Getting year, month, and day
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // Creating a DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    // Setting the selected date to the TextInputEditText
                    selectedDateEditText.setText(
                        String.format(
                            "%02d-%02d-%04d",
                            dayOfMonth,
                            monthOfYear + 1,
                            year
                        )
                    )
                },
                // Passing year, month, and day for the selected date
                year,
                month,
                day
            )
            // Showing the DatePickerDialog
            datePickerDialog.show()
        }

    }
}
