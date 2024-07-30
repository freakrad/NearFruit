package com.example.nearfruit.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.nearfruit.R
import com.example.nearfruit.databinding.ActivityLoginSellerBinding
import com.google.firebase.auth.FirebaseAuth

class LoginSellerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginSellerBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginSellerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnHaventLogin.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            // Validate Email
            if (email.isEmpty()) {
                binding.edLoginEmail.error = "Email harus diisi!"
                binding.edLoginEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edLoginEmail.error = "Email tidak valid"
                binding.edLoginEmail.requestFocus()
                return@setOnClickListener
            }

            // Validate Password
            if (password.isEmpty()) {
                binding.edLoginPassword.error = "Password harus diisi!"
                binding.edLoginPassword.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 8) {
                binding.edLoginPassword.error = "Password minimal 8 karakter!"
                binding.edLoginPassword.requestFocus()
                return@setOnClickListener
            }

            // Show loading
            showLoading(true)

            // Perform Firebase Login
            loginWithFirebase(email, password)
        }

    }

    private fun loginWithFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                // Hide loading
                showLoading(false)

                if (task.isSuccessful) {
                    // Login success
                    showAlert(getString(R.string.success), getString(R.string.success_login), true)
                } else {
                    // Login failed
                    showAlert(getString(R.string.fail), getString(R.string.failed_login), false)
                }
            }
    }

    private fun showAlert(title: String, message: String, isSuccess: Boolean) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(getString(R.string.ok)) { _, _ ->
                if (isSuccess) navigateToMain()
            }
            create()
            show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun navigateToMain() {
        val intent = Intent(this@LoginSellerActivity, TokoActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(intent)
        finish()
    }

}
