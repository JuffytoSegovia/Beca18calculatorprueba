package com.example.beca18calculator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.beca18calculator.databinding.ActivityPrePagTwoBinding

class PrePagTwoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrePagTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrePagTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextPagF.setOnClickListener{
            startActivity(Intent(this@PrePagTwoActivity, PrePagThreeActivity::class.java))
        }

    }
}