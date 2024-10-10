package com.example.beca18calculator

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Message
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.beca18calculator.databinding.ActivityPrePagOneBinding
import com.example.beca18calculator.databinding.ActivityPrePagThreeBinding

class PrePagThreeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrePagThreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrePagThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        val nombre = bundle?.getString("nombre")
        val scorePagOne = bundle?.getInt("scorePagOne")
        val scorePagTwo = bundle?.getInt("scorePagTwo")

        binding.gFPagOne.setOnClickListener{
            startActivity(Intent(this@PrePagThreeActivity, PrePagOneActivity::class.java))
        }

        binding.gFPagTwo.setOnClickListener{
            startActivity(Intent(this@PrePagThreeActivity, PrePagTwoActivity::class.java))
        }

        binding.gFPagThree.setOnClickListener{
            startActivity(Intent(this@PrePagThreeActivity, PrePagThreeActivity::class.java))
        }

        binding.nextPagF.setOnClickListener {
            val message : String = "Felicidades " + nombre + " tu puntaje en el proceso de seleccion es"
            val message2 : Int = (calculateScore() + scorePagOne!!.toInt() + scorePagTwo!!.toInt())
            showCustomDialogBox(message, message2)
        }

    }

    private fun calculateScore(): Int {
        var score = 0

        // Otras condiciones priorizables (O) (máximo 25 puntos)
        var priorizableScore = 0
        if (binding.discapacidadCheckbox.isChecked) priorizableScore += 5
        if (binding.bomberosCheckbox.isChecked) priorizableScore += 5
        if (binding.voluntariosCheckbox.isChecked) priorizableScore += 5
        if (binding.comunidadNativaCheckbox.isChecked) priorizableScore += 5
        if (binding.metalesPesadosCheckbox.isChecked) priorizableScore += 5
        if (binding.poblacionBeneficiariaCheckbox.isChecked) priorizableScore += 5
        if (binding.orfandadCheckbox.isChecked) priorizableScore += 5
        if (binding.desproteccionCheckbox.isChecked) priorizableScore += 5
        if (binding.agenteSaludCheckbox.isChecked) priorizableScore += 5
        score += priorizableScore.coerceAtMost(25)

        return score
    }

    private fun showCustomDialogBox(message: String, message2: Int){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.tvMessage)
        val tvPuntaje: TextView = dialog.findViewById(R.id.tvPuntaje)
        val btnYes : Button = dialog.findViewById(R.id.btnYes)
        val btnNo : Button = dialog.findViewById(R.id.btnNo)

        tvMessage.text = message
        tvPuntaje.text = message2.toString()

        btnYes.setOnClickListener{
            startActivity(Intent(this@PrePagThreeActivity, HomeActivity::class.java))
        }

        btnNo.setOnClickListener{
            dialog.dismiss()
        }

        dialog.show()
    }

}