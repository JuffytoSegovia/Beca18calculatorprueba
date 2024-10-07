package com.example.beca18calculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.beca18calculator.databinding.ActivityPreselectionCalculatorBinding

class PreselectionCalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreselectionCalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreselectionCalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        setupButtons()
    }

    private fun setupSpinners() {
        // Configurar spinner de modalidad
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

        // Listener para mostrar/ocultar opciones específicas de EIB
        binding.modalitySpinner.setOnItemClickListener { _, _, position, _ ->
            binding.eibOptionsLayout.visibility = if (modalities[position] == "EIB") View.VISIBLE else View.GONE
        }
    }

    private fun setupButtons() {
        binding.calculateButton.setOnClickListener {
            if (validateInputs()) {
                val score = calculateScore()
                val intent = Intent(this, ReportActivity::class.java)
                intent.putExtra("SCORE", score)
                intent.putExtra("REPORT_DETAILS", generateReportDetails())
                startActivity(intent)
            }
        }

        binding.clearButton.setOnClickListener {
            clearForm()
        }

        binding.quintilInfoButton.setOnClickListener {
            showQuintilInfo()
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

    private fun calculateScore(): Int {
        var score = 0

        // ENP (E)
        score += binding.enpInput.text.toString().toInt()

        // SISFOH (S)
        when (binding.sisfohSpinner.text.toString()) {
            "Pobreza extrema" -> score += 5
            "Pobreza" -> {
                if (binding.modalitySpinner.text.toString() != "Ordinaria") {
                    score += 2
                }
            }
        }

        // Tasa de transición (T)
        score += getQuintilScore(binding.departmentSpinner.text.toString())

        // Actividades extracurriculares (A) (máximo 10 puntos)
        var extracurricularScore = 0
        if (binding.concursoNacionalCheckbox.isChecked) extracurricularScore += 5
        if (binding.concursoParticipacionCheckbox.isChecked) extracurricularScore += 2
        if (binding.juegosNacionalesCheckbox.isChecked) extracurricularScore += 5
        if (binding.juegosParticipacionCheckbox.isChecked) extracurricularScore += 2
        score += extracurricularScore.coerceAtMost(10)

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

        // EIB específico
        if (binding.modalitySpinner.text.toString() == "EIB") {
            when (binding.eibLanguageSpinner.text.toString()) {
                "Primera prioridad" -> score += 10
                "Segunda prioridad" -> score += 5
            }
        }

        // Ajustar según el puntaje máximo de la modalidad
        val maxScore = if (binding.modalitySpinner.text.toString() == "EIB") 180 else 170
        return score.coerceAtMost(maxScore)
    }

    private fun generateReportDetails(): String {
        val name = binding.nameInput.text.toString()
        val modality = binding.modalitySpinner.text.toString()
        val enp = binding.enpInput.text.toString().toInt()
        val sisfoh = binding.sisfohSpinner.text.toString()
        val department = binding.departmentSpinner.text.toString()
        val quintilScore = getQuintilScore(department)

        var report = "Reporte de Preselección para $name\n\n"
        report += "Modalidad: $modality\n"
        report += "E (ENP): $enp puntos\n"
        report += "S (SISFOH): ${if (sisfoh == "Pobreza extrema") "5" else if (sisfoh == "Pobreza" && modality != "Ordinaria") "2" else "0"} puntos\n"
        report += "T (Tasa de transición): $quintilScore puntos\n"

        // Actividades extracurriculares
        var extracurricularScore = 0
        if (binding.concursoNacionalCheckbox.isChecked) extracurricularScore += 5
        if (binding.concursoParticipacionCheckbox.isChecked) extracurricularScore += 2
        if (binding.juegosNacionalesCheckbox.isChecked) extracurricularScore += 5
        if (binding.juegosParticipacionCheckbox.isChecked) extracurricularScore += 2
        extracurricularScore = extracurricularScore.coerceAtMost(10)
        report += "A (Actividades extracurriculares): $extracurricularScore puntos\n"

        // Otras condiciones priorizables
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
        priorizableScore = priorizableScore.coerceAtMost(25)
        report += "O (Otras condiciones priorizables): $priorizableScore puntos\n"

        if (modality == "EIB") {
            val eibScore = if (binding.eibLanguageSpinner.text.toString() == "Primera prioridad") 10 else 5
            report += "EIB: $eibScore puntos\n"
        }

        report += "\nFórmula aplicada: PS = E + S + T + A + O"
        if (modality == "EIB") {
            report += " + EIB"
        }

        return report
    }

    private fun getQuintilScore(department: String): Int {
        return when (department) {
            "Amazonas", "Ucayali", "Ayacucho", "Puno", "Loreto" -> 10
            "San Martín", "Cusco", "Huánuco", "Apurímac", "Huancavelica" -> 7
            "Áncash", "Tacna", "Madre de Dios", "Moquegua", "Pasco", "Cajamarca" -> 5
            "Arequipa", "Piura", "Junín", "Tumbes" -> 2
            else -> 0
        }
    }

    private fun clearForm() {
        binding.nameInput.text?.clear()
        binding.modalitySpinner.text?.clear()
        binding.enpInput.text?.clear()
        binding.sisfohSpinner.text?.clear()
        binding.departmentSpinner.text?.clear()
        binding.eibLanguageSpinner.text?.clear()
        binding.eibOptionsLayout.visibility = View.GONE
        binding.concursoNacionalCheckbox.isChecked = false
        binding.concursoParticipacionCheckbox.isChecked = false
        binding.juegosNacionalesCheckbox.isChecked = false
        binding.juegosParticipacionCheckbox.isChecked = false
        binding.discapacidadCheckbox.isChecked = false
        binding.bomberosCheckbox.isChecked = false
        binding.voluntariosCheckbox.isChecked = false
        binding.comunidadNativaCheckbox.isChecked = false
        binding.metalesPesadosCheckbox.isChecked = false
        binding.poblacionBeneficiariaCheckbox.isChecked = false
        binding.orfandadCheckbox.isChecked = false
        binding.desproteccionCheckbox.isChecked = false
        binding.agenteSaludCheckbox.isChecked = false
    }

    private fun showQuintilInfo() {
        val quintilInfo = """
            Quintil 1 (10 puntos): Amazonas, Ucayali, Ayacucho, Puno, Loreto
            Quintil 2 (7 puntos): San Martín, Cusco, Huánuco, Apurímac, Huancavelica
            Quintil 3 (5 puntos): Áncash, Tacna, Madre de Dios, Moquegua, Pasco, Cajamarca
            Quintil 4 (2 puntos): Arequipa, Piura, Junín, Tumbes
            Quintil 5 (0 puntos): Ica, Lambayeque, Lima, La Libertad
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("Información de Quintiles")
            .setMessage(quintilInfo)
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}