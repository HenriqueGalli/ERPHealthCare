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
    private var isNewUser = true

    private val gender = ArrayList<String>()
    private val bloodType = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cadastro_paciente)
        binding.viewModel = this
        setupGenderSpinner()
        setupBloodTypeSpinner()
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
            binding.spinnerGenero.setSelection(dataAdapter.getPosition(paciente?.sexo))
        else
            binding.spinnerGenero.setSelection(0)
    }

    private fun setupBloodTypeSpinner() {
        bloodType.add("A")
        bloodType.add("B")
        bloodType.add("AB")
        bloodType.add("O")

        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                this,
                R.layout.simple_spinner_dropdown_item,
                bloodType
            )
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipoSanguineo.adapter = dataAdapter
        if (intent.hasExtra("PACIENTE"))
            binding.spinnerTipoSanguineo.setSelection(dataAdapter.getPosition(paciente?.tipoSanguineo))
    }

    fun cadastrar() {
        if (validateFields()) {
            if (isNewUser) {
                val paciente = Paciente(
                    nome = binding.editTextNome.editText?.text.toString(),
                    cpf = binding.editTextCpf.editText?.text.toString(),
                    nomeMae = binding.editTextNomeMae.editText?.text.toString(),
                    email = binding.editTextEmail.editText?.text.toString(),
                    telefone = binding.editTextTelefone.editText?.text.toString(),
                    enderecoCompleto = binding.editTextEndereco.editText?.text.toString(),
                    naturalidade = binding.editTextNaturalidade.editText?.text.toString(),
                    peso = binding.editTextPeso.editText?.text.toString().toFloat(),
                    altura = binding.editTextAltura.editText?.text.toString().toFloat(),
                    senha = binding.editTextSenha.editText?.text.toString(),
                    dataNascimento = getDate(),
                    sexo = binding.spinnerGenero.selectedItem.toString(),
                    tipoSanguineo = binding.spinnerTipoSanguineo.toString()
                    )
            }
            //Chamar ViewModel com chamada de api de cadastro
        }
    }

    private fun validateFields(): Boolean {
        if (binding.editTextNome.editText?.text.toString().isEmpty()) {
            binding.editTextNome.error = "Campo Obrigatório"
            return false
        }
        if (binding.editTextCpf.editText?.text.toString().isEmpty()) {
            binding.editTextCpf.error = "Campo Obrigatório"
            return false
        }
        if (binding.editTextNomeMae.editText?.text.toString().isEmpty()) {
            binding.editTextNomeMae.error = "Campo Obrigatório"
            return false
        }
        if (binding.editTextEmail.editText?.text.toString().isEmpty()) {
            binding.editTextEmail.error = "Campo Obrigatório"
            return false
        }
        if (binding.editTextTelefone.editText?.text.toString().isEmpty()) {
            binding.editTextTelefone.error = "Campo Obrigatório"
            return false
        }
        if (binding.editTextEndereco.editText?.text.toString().isEmpty()) {
            binding.editTextEndereco.error = "Campo Obrigatório"
            return false
        }
        if (binding.editTextNaturalidade.editText?.text.toString().isEmpty()) {
            binding.editTextNaturalidade.error = "Campo Obrigatório"
            return false
        }
        if (binding.editTextPeso.editText?.text.toString().isEmpty()) {
            binding.editTextPeso.error = "Campo Obrigatório"
            return false
        }
        if (binding.editTextAltura.editText?.text.toString().isEmpty()) {
            binding.editTextAltura.error = "Campo Obrigatório"
            return false
        }
        if (binding.editTextSenha.editText?.text.toString().isEmpty()) {
            binding.editTextSenha.error = "Campo Obrigatório"
            return false
        }
        return true
    }

    private fun getDate(): String {
        val year = binding.simpleDatePicker.year
        val month = setupMonth()
        val day = setupDay()
        return "$year-$month-$day" + "T00:00:00"
    }

    private fun setupDay(): String {
        val day = binding.simpleDatePicker.dayOfMonth
        return if (day.toString().length == 2)
            day.toString()
        else "0$day"
    }

    private fun setupMonth(): String {
        val year = binding.simpleDatePicker.month + 1
        return if (year.toString().length == 2)
            year.toString()
        else "0$year"
    }

}