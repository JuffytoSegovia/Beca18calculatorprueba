package com.example.beca18calculator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.beca18calculator.databinding.ActivityCalculatorsBinding

class CalculatorsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCalculatorsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculatorsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.goCals.setOnClickListener{
            startActivity(Intent(this@CalculatorsActivity, CalculatorsActivity::class.java))
        }

        binding.goHome.setOnClickListener{
            startActivity(Intent(this@CalculatorsActivity, HomeActivity::class.java))
        }

        binding.goCredits.setOnClickListener{
            startActivity(Intent(this@CalculatorsActivity, CreditsActivity::class.java))
        }

        binding.goPreCal.setOnClickListener{
            startActivity(Intent(this@CalculatorsActivity, PrePagOneActivity::class.java))
        }
    }
}