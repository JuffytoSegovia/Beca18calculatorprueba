package com.example.beca18calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.beca18calculator.databinding.ActivityReportBinding

class ReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra("SCORE", 0)
        val reportDetails = intent.getStringExtra("REPORT_DETAILS") ?: ""

        binding.scoreTextView.text = "Tu puntaje total de preselección es: $score"
        binding.reportDetailsTextView.text = reportDetails

        binding.closeButton.setOnClickListener {
            finish()
        }
    }
}