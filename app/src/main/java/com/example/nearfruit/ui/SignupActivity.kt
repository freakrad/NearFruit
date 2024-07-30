package com.example.nearfruit.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.nearfruit.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            val username = binding.edSignupUsername.text.toString()
            val email = binding.edSignupEmail.text.toString()
            val password = binding.edSignupPassword.text.toString()

            //Validasi Username
            if (username.isEmpty()) {
                binding.edSignupUsername.error = "Username harus diisi!"
                binding.edSignupUsername.requestFocus()
                return@setOnClickListener
            }

            //Validasi Email
            if (email.isEmpty()) {
                binding.edSignupEmail.error = "Email harus diisi!"
                binding.edSignupEmail.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.edSignupEmail.error = "Email tidak valid"
                binding.edSignupEmail.requestFocus()
                return@setOnClickListener
            }

            //Validasi Password
            if (password.isEmpty()) {
                binding.edSignupPassword.error = "Password harus diisi!"
                binding.edSignupPassword.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 8) {
                binding.edSignupPassword.error = "Password minimal 8 karakter!"
                binding.edSignupPassword.requestFocus()
                return@setOnClickListener
            }

            SignupFirebase(email, password)
        }
    }

    private fun SignupFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginSellerActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}