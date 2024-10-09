package com.example.beca18calculator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.beca18calculator.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goCals.setOnClickListener{
            startActivity(Intent(this@HomeActivity, CalculatorsActivity::class.java))
        }

        binding.goHome.setOnClickListener{
            startActivity(Intent(this@HomeActivity, HomeActivity::class.java))
        }
    }
}