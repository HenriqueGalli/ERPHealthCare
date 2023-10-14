package com.project.erphealthcare.ui.paciente.cadastro

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.preventivewelfare.api.result.DeleteUserResult
import br.com.preventivewelfare.api.result.EditUserResult
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.CreatePacienteResult
import com.project.erphealthcare.databinding.ActivityCadastroPacienteBinding
import com.project.erphealthcare.utils.CpfUtils

class CadastroPacienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroPacienteBinding

    private val viewModel: CadastroPacienteViewModel =
        CadastroPacienteViewModelFactory()
            .create(CadastroPacienteViewModel::class.java)

    private var paciente: Paciente? = null
    private var token: String? = ""
    private var isNewUser = true

    private val gender = ArrayList<String>()
    private val bloodType = ArrayList<String>()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cadastro_paciente)
        binding.viewModel = this
        setupListener()
        setupObserver()
        if (intent.hasExtra("TOKEN"))
            token = intent.getStringExtra("TOKEN")
        if (intent.hasExtra("PACIENTE")) {
            setupEditUser()
        } else {
            binding.editTextAltura.editText?.setText("")
            binding.editTextPeso.editText?.setText("")
            setupFields()
        }

    }

    private fun setupEditUser() {
        paciente = intent.getSerializableExtra("PACIENTE") as Paciente
        binding.user = paciente
        setupFields()
        binding.tvCadastrarPaciente.text = "Editar dados paciente"
        binding.buttonExcluir.visibility = View.VISIBLE
        binding.simpleDatePicker.updateDate(
            paciente!!.getBirthYear(),
            paciente!!.getBirthMonth(), paciente!!.getBirthDay()
        )
        binding.buttonEnviar.text = "Salvar"
        isNewUser = false
    }

    private fun setupFields() {
        setupBloodTypeSpinner()
        setupGenderSpinner()
    }

    private fun setupListener() {
        binding.editTextCpf.addTextChangedListener(
            CpfUtils.mask(
                "###.###.###-##",
                binding.editTextCpf
            )
        )
        binding.editTextTelefone.editText?.addTextChangedListener(
            CpfUtils.mask(
                "(##)#####-####",
                binding.editTextTelefone.editText!!
            )
        )
        binding.buttonExcluir.setOnClickListener {
            excluir()
        }
    }

    private fun setupObserver() {
        viewModel.cadastrarLiveData.observe(this) { res ->
            when (res) {
                is CreatePacienteResult.Success -> userCreated()
                else -> userCreatedError()
            }
        }

        viewModel.editarLiveData.observe(this) { res ->
            when (res) {
                is EditUserResult.Success -> userEdited()
                else -> userCreatedError()
            }
        }
        viewModel.excluirLiveData.observe(this) { res ->
            when (res) {
                is DeleteUserResult.Success -> userDeletedSuccess()
                is DeleteUserResult.ServerError -> userDeletedError()
            }
        }
    }

    private fun userEdited() {
        val fragment = PopUpCadastroActivity(
            isError = false,
            isNewUser = false,
            context = this,
            token = token ?: ""
        )
        fragment.show(supportFragmentManager, "My Fragment")
    }

    private fun userDeletedError() {
        val fragment =
            PopUpCadastroActivity(
                isError = true,
                isNewUser = false,
                isExcludedMessage = true,
                context = this
            )
        fragment.show(supportFragmentManager, "My Fragment")
    }

    private fun userDeletedSuccess() {
        val fragment =
            PopUpCadastroActivity(
                isError = false,
                isNewUser = false,
                isExcludedMessage = true,
                context = this
            )
        fragment.show(supportFragmentManager, "My Fragment")
    }

    private fun userCreatedError() {
        val fragment = PopUpCadastroActivity(isError = true, context = this)
        fragment.show(supportFragmentManager, "My Fragment")
    }

    private fun userCreated() {
        val fragment = PopUpCadastroActivity(isError = false, context = this)
        fragment.show(supportFragmentManager, "My Fragment")
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
        bloodType.add("A-")
        bloodType.add("A+")
        bloodType.add("B-")
        bloodType.add("B+")
        bloodType.add("AB-")
        bloodType.add("AB+")
        bloodType.add("O-")
        bloodType.add("O+")

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
                    cpf = binding.editTextCpf.text.toString().replace("-", "").replace(".", ""),
                    nomeMae = binding.editTextNomeMae.editText?.text.toString(),
                    email = binding.editTextEmail.editText?.text.toString(),
                    telefone = binding.editTextTelefone.editText?.text.toString().replace("(", "")
                        .replace(")", "").replace("-", ""),
                    enderecoCompleto = binding.editTextEndereco.editText?.text.toString(),
                    naturalidade = binding.editTextNaturalidade.editText?.text.toString(),
                    peso = binding.editTextPeso.editText?.text.toString().toFloat(),
                    altura = binding.editTextAltura.editText?.text.toString().toFloat(),
                    senha = binding.editTextSenha.editText?.text.toString(),
                    dataNascimento = getDate(),
                    sexo = binding.spinnerGenero.selectedItem.toString(),
                    tipoSanguineo = binding.spinnerTipoSanguineo.selectedItem.toString()
                )
                viewModel.cadastrarPaciente(paciente)
            } else {
                val paciente = Paciente(
                    nome = binding.editTextNome.editText?.text.toString(),
                    cpf = binding.editTextCpf.text.toString().replace("-", "").replace(".", ""),
                    nomeMae = binding.editTextNomeMae.editText?.text.toString(),
                    email = binding.editTextEmail.editText?.text.toString(),
                    telefone = binding.editTextTelefone.editText?.text.toString().replace("(", "")
                        .replace(")", "").replace("-", ""),
                    enderecoCompleto = binding.editTextEndereco.editText?.text.toString(),
                    naturalidade = binding.editTextNaturalidade.editText?.text.toString(),
                    peso = binding.editTextPeso.editText?.text.toString().toFloat(),
                    altura = binding.editTextAltura.editText?.text.toString().toFloat(),
                    senha = binding.editTextSenha.editText?.text.toString(),
                    dataNascimento = getDate(),
                    sexo = binding.spinnerGenero.selectedItem.toString(),
                    tipoSanguineo = binding.spinnerTipoSanguineo.toString()
                )
                viewModel.editar(paciente)
            }
        }
    }

    fun excluir() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Você realmente quer deletar o usuário?")
            .setCancelable(false)
            .setPositiveButton("Sim") { dialog, id ->
                viewModel.deletar()
            }
            .setNegativeButton("Não") { dialog, id ->
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }

    private fun validateFields(): Boolean {
        if (binding.editTextNome.editText?.text.toString().isEmpty()) {
            binding.editTextNome.error = "Campo Obrigatório"
            return false
        }
        if (binding.editTextCpf.text.toString().isEmpty()) {
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