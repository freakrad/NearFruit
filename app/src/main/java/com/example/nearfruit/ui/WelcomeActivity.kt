package com.example.nearfruit.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nearfruit.R
import com.example.nearfruit.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction(){
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginSellerActivity::class.java))
        }
    }
}