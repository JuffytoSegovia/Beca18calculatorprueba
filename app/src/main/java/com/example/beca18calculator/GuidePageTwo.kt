package com.example.beca18calculator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.beca18calculator.databinding.ActivityGuidePageTwoBinding

class GuidePageTwo : AppCompatActivity() {
    private lateinit var binding: ActivityGuidePageTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityGuidePageTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gPagOne.setOnClickListener{
            startActivity(Intent(this@GuidePageTwo, GuidePagOne::class.java))
        }

        binding.gPagTwo.setOnClickListener{
            startActivity(Intent(this@GuidePageTwo, GuidePageTwo::class.java))
        }

        binding.gPagThree.setOnClickListener{
            startActivity(Intent(this@GuidePageTwo, GuidePageThree::class.java))
        }

        binding.nextPagTwo.setOnClickListener{
            startActivity(Intent(this@GuidePageTwo, GuidePageThree::class.java))
        }
    }
}