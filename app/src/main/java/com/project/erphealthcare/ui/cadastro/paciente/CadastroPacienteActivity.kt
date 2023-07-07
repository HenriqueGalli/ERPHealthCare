package com.project.erphealthcare.ui.cadastro.paciente

import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.project.erphealthcare.data.model.Paciente
import androidx.databinding.DataBindingUtil
import com.project.erphealthcare.R
import com.project.erphealthcare.databinding.ActivityCadastroPacienteBinding

class CadastroPacienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroPacienteBinding
    private var paciente: Paciente? = null

    private val gender = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cadastro_paciente)
        binding.viewModel = this
        setupGenderSpinner()
        if (intent.hasExtra("PACIENTE")) {
            paciente = intent.getSerializableExtra("PACIENTE", Paciente::class.java)
        }

    }

    private fun setupGenderSpinner() {
        gender.add("MASCULINO")
        gender.add("FEMININO")
        gender.add("OUTRO")

        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                this,
                R.layout.simple_spinner_dropdown_item,
                gender
            )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGenero.adapter = dataAdapter
        if (intent.hasExtra("PACIENTE"))
            binding.spinnerGenero.setSelection(0)
    }

}