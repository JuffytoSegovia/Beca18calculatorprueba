package com.example.beca18calculator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.beca18calculator.databinding.ActivityGuidePageThreeBinding

class GuidePageThree : AppCompatActivity() {
    private lateinit var binding: ActivityGuidePageThreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityGuidePageThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.gPagOne.setOnClickListener{
            startActivity(Intent(this@GuidePageThree, GuidePagOne::class.java))
        }

        binding.gPagTwo.setOnClickListener{
            startActivity(Intent(this@GuidePageThree, GuidePageTwo::class.java))
        }

        binding.gPagThree.setOnClickListener{
            startActivity(Intent(this@GuidePageThree, GuidePageThree::class.java))
        }

        binding.nextPagTwo.setOnClickListener{
            startActivity(Intent(this@GuidePageThree, HomeActivity::class.java))
        }
    }
}