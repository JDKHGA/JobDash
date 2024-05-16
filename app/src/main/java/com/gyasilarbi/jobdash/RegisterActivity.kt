package com.gyasilarbi.jobdash

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.gyasilarbi.jobdash.databinding.ActivityRegisterBinding
import java.security.MessageDigest
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var selectedDateEditText: TextInputEditText

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        binding.signInButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Initialize views
        selectedDateEditText = binding.idTVSelectedDate
        val cityEt: AutoCompleteTextView = binding.cityEt

        // Create an array of cities
        val cities = arrayOf("Accra", "Kumasi", "Takoradi", "Cape Coast", "Sunyani")

        // Create an adapter for the AutoCompleteTextView
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, cities)

        // Set the adapter to the AutoCompleteTextView
        cityEt.setAdapter(adapter)

        // Set OnClickListener to Sign Up button
        binding.signUpButton.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passwordEt.text.toString().trim()
            val confirmPass = binding.confirmPassEt.text.toString().trim()
            val firstName = binding.firstNameEt.text.toString().trim()
            val lastName = binding.lastNameEt.text.toString().trim()
            val city = cityEt.text.toString().trim()
            val dateOfBirth = selectedDateEditText.text.toString().trim()

            // Validate fields
            if (email.isEmpty() || password.isEmpty() || confirmPass.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || city.isEmpty() || dateOfBirth.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (city !in cities) {
                Toast.makeText(this, "Please select a valid city", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPass) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Add password length constraint
            if (password.length < 8 || password.length > 20) {
                Toast.makeText(this, "Password must be between 8 and 20 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Hash the password
            val hashedPassword = hashPassword(password)

            // Create user with email and hashed password using Firebase Authentication
            firebaseAuth.createUserWithEmailAndPassword(email, hashedPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration successful, navigate to next screen
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Registration failed
                        Toast.makeText(this, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

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

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hashedBytes.joinToString("") { "%02x".format(it) }
    }
}
