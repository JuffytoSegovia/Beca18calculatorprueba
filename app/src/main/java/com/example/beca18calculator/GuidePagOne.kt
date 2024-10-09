package com.example.beca18calculator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beca18calculator.databinding.ActivityGuidePagOneBinding
import com.example.beca18calculator.databinding.ActivitySplashBinding

class GuidePagOne : AppCompatActivity() {
    private lateinit var binding: ActivityGuidePagOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityGuidePagOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gPagOne.setOnClickListener{
            startActivity(Intent(this@GuidePagOne, GuidePagOne::class.java))
        }

        binding.gPagTwo.setOnClickListener{
            startActivity(Intent(this@GuidePagOne, GuidePageTwo::class.java))
        }

        binding.gPagThree.setOnClickListener{
            startActivity(Intent(this@GuidePagOne, GuidePageThree::class.java))
        }

        binding.nextPagTwo.setOnClickListener{
            startActivity(Intent(this@GuidePagOne, GuidePageTwo::class.java))
        }

    }
}