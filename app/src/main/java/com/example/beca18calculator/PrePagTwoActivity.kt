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

        val bundle = intent.extras
        val nombre = bundle?.getString("nombre")
        val scorePagOne = bundle?.getInt("scorePagOne")

        binding.gFPagOne.setOnClickListener{
            startActivity(Intent(this@PrePagTwoActivity, PrePagOneActivity::class.java))
        }

        binding.gFPagTwo.setOnClickListener{
            startActivity(Intent(this@PrePagTwoActivity, PrePagTwoActivity::class.java))
        }

        binding.gFPagThree.setOnClickListener{
            startActivity(Intent(this@PrePagTwoActivity, PrePagThreeActivity::class.java))
        }

        binding.nextPagF.setOnClickListener {
            val intent = Intent(this, PrePagThreeActivity::class.java)
            intent.putExtra("nombre", nombre)
            intent.putExtra("scorePagOne", scorePagOne)
            intent.putExtra("scorePagTwo", calculateScore())
            startActivity(intent)
        }
    }

    private fun calculateScore(): Int {
        var score:Int = 0
        // Actividades extracurriculares (A) (máximo 10 puntos)
        var extracurricularScore = 0
        if (binding.concursoNacionalCheckbox.isChecked) extracurricularScore += 5
        if (binding.concursoParticipacionCheckbox.isChecked) extracurricularScore += 2
        if (binding.juegosNacionalesCheckbox.isChecked) extracurricularScore += 5
        if (binding.juegosParticipacionCheckbox.isChecked) extracurricularScore += 2
        score += extracurricularScore.coerceAtMost(10)
        return score
    }
}