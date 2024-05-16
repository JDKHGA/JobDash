package com.gyasilarbi.jobdash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.gyasilarbi.jobdash.databinding.ActivityLoginBinding
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val sharedPref by lazy {
        getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase Authentication
        firebaseAuth = FirebaseAuth.getInstance()

        // Check if login info is saved
        checkSavedLoginInfo()

        binding.signUpButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Set OnClickListener to Login button
        binding.loginButton.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val password = binding.passwordEt.text.toString().trim()

            // Validate fields
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Hash the password for authentication
            val hashedPassword = hashPassword(password)

            // Sign in user with email and hashed password using Firebase Authentication
            firebaseAuth.signInWithEmailAndPassword(email, hashedPassword)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Save login info if "Remember Me" is checked
                        if (binding.rememberMeCheckbox.isChecked) {
                            saveLoginInfo(email, password) // Save plain password
                        } else {
                            clearLoginInfo()
                        }

                        // Login successful, navigate to next screen
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, HomeActivity::class.java) // Change HomeActivity to your desired destination
                        startActivity(intent)
                        finish()
                    } else {
                        // Login failed
                        Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun checkSavedLoginInfo() {
        val savedEmail = sharedPref.getString("email", null)
        val savedPassword = sharedPref.getString("password", null)
        if (!savedEmail.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
            binding.emailEt.setText(savedEmail)
            binding.passwordEt.setText(savedPassword)
            binding.rememberMeCheckbox.isChecked = true
        }
    }

    private fun saveLoginInfo(email: String, password: String) {
        val editor = sharedPref.edit()
        editor.putString("email", email)
        editor.putString("password", password) // Save plain password
        editor.apply()
    }

    private fun clearLoginInfo() {
        val editor = sharedPref.edit()
        editor.remove("email")
        editor.remove("password")
        editor.apply()
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hashedBytes.joinToString("") { "%02x".format(it) }
    }
}
