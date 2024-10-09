package com.example.beca18calculator

import android.content.Intent
import android.os.Bundle
import com.example.beca18calculator.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity() {
    private lateinit var binding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.splashBtn.setOnClickListener{
            startActivity(Intent(this@SplashActivity, GuidePagOne::class.java))
        }

    }
}