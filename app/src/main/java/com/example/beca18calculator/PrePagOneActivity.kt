package com.example.beca18calculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beca18calculator.databinding.ActivityPrePagOneBinding

class PrePagOneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrePagOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrePagOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val modalities = arrayOf("Ordinaria", "CNA y PA", "EIB", "Protección", "FF. AA.", "VRAEM", "Huallaga", "REPARED")
        val modalityAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, modalities)
        binding.modalitySpinner.setAdapter(modalityAdapter)

        // Configurar spinner de SISFOH
        val sisfohCategories = arrayOf("Pobreza extrema", "Pobreza", "No pobre")
        val sisfohAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, sisfohCategories)
        binding.sisfohSpinner.setAdapter(sisfohAdapter)

        // Configurar spinner de departamentos
        val departments = arrayOf("Amazonas", "Áncash", "Apurímac", "Arequipa", "Ayacucho", "Cajamarca", "Cusco", "Huancavelica", "Huánuco", "Ica", "Junín", "La Libertad", "Lambayeque", "Lima", "Loreto", "Madre de Dios", "Moquegua", "Pasco", "Piura", "Puno", "San Martín", "Tacna", "Tumbes", "Ucayali")
        val departmentAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, departments)
        binding.departmentSpinner.setAdapter(departmentAdapter)

        // Configurar spinner de lengua originaria (EIB)
        val eibLanguages = arrayOf("Primera prioridad", "Segunda prioridad")
        val eibLanguageAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, eibLanguages)
        binding.eibLanguageSpinner.setAdapter(eibLanguageAdapter)

        binding.modalitySpinner.setOnItemClickListener { _, _, position, _ ->
            binding.eibOptionsLayout.visibility = if (modalities[position] == "EIB") View.VISIBLE else View.GONE
        }

        binding.nextPagF.setOnClickListener {
            if (validateInputs()) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateInputs(): Boolean {
        if (binding.modalitySpinner.text.isNullOrEmpty()) {
            showError("Por favor, selecciona una modalidad")
            return false
        }
        if (binding.nameInput.text.toString().isEmpty()) {
            showError("Por favor, ingresa tu nombre")
            return false
        }
        if (binding.enpInput.text.toString().isEmpty()) {
            showError("Por favor, ingresa tu puntaje ENP")
            return false
        }
        val enp = binding.enpInput.text.toString().toIntOrNull()
        if (enp == null || enp < 0 || enp > 120 || enp % 2 != 0) {
            showError("El puntaje ENP debe ser un número par entre 0 y 120")
            return false
        }
        if (binding.sisfohSpinner.text.isNullOrEmpty()) {
            showError("Por favor, selecciona una clasificación SISFOH")
            return false
        }
        if (binding.departmentSpinner.text.isNullOrEmpty()) {
            showError("Por favor, selecciona un departamento")
            return false
        }
        if (binding.modalitySpinner.text.toString() == "EIB" && binding.eibLanguageSpinner.text.isNullOrEmpty()) {
            showError("Para la modalidad EIB, debes seleccionar una lengua originaria")
            return false
        }
        return true
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}