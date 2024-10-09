package com.example.beca18calculator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.beca18calculator.databinding.ActivityCreditsBinding

class CreditsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreditsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goCals.setOnClickListener{
            startActivity(Intent(this@CreditsActivity, CalculatorsActivity::class.java))
        }

        binding.goHome.setOnClickListener{
            startActivity(Intent(this@CreditsActivity, HomeActivity::class.java))
        }

        binding.goCredits.setOnClickListener{
            startActivity(Intent(this@CreditsActivity, CreditsActivity::class.java))
        }
    }
}