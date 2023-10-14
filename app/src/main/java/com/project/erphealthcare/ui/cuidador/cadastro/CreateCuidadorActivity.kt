package com.project.erphealthcare.ui.cuidador.cadastro

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import br.com.preventivewelfare.api.result.DeleteUserResult
import br.com.preventivewelfare.api.result.EditUserResult
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.Cuidador
import com.project.erphealthcare.data.result.CreateCuidadorResult
import com.project.erphealthcare.databinding.ActivityCreateCuidadorBinding
import com.project.erphealthcare.utils.CpfUtils

class CreateCuidadorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateCuidadorBinding

    private val viewModel: CadastroCuidadorViewModel =
        CadastroCuidadorViewModelFactory()
            .create(CadastroCuidadorViewModel::class.java)

    private var cuidador: Cuidador? = null
    private var isNewUser = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_cuidador)
        binding.viewModel = this
        setupListener()
        setupObserver()
        if (intent.hasExtra("CUIDADOR")) {
            setupEditUser()
        }
    }

    private fun setupEditUser() {
        cuidador = intent.getSerializableExtra("CUIDADOR") as Cuidador
        binding.user = cuidador
        binding.tvCadastrarPaciente.text = "Editar dados do cuidador"
        binding.buttonExcluir.visibility = View.VISIBLE
        binding.buttonEnviar.text = "Salvar"
        isNewUser = false
    }

    private fun setupListener() {
        binding.editTextCpf.addTextChangedListener(
            CpfUtils.mask(
                "###.###.###-##",
                binding.editTextCpf
            )
        )
        binding.buttonExcluir.setOnClickListener {
            excluir()
        }
    }

    private fun setupObserver() {
        viewModel.cadastrarLiveData.observe(this) { res ->
            when (res) {
                is CreateCuidadorResult.Success -> userCreated()
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
        val fragment =
            PopUpCadastroCuidadorActivity(isError = false, isNewUser = false, context = this)
        fragment.show(supportFragmentManager, "My Fragment")
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

    private fun userDeletedError() {
        val fragment =
            PopUpCadastroCuidadorActivity(
                isError = true,
                isNewUser = false,
                isExcludedMessage = true,
                context = this
            )
        fragment.show(supportFragmentManager, "My Fragment")
    }

    private fun userDeletedSuccess() {
        val fragment =
            PopUpCadastroCuidadorActivity(
                isError = false,
                isNewUser = false,
                isExcludedMessage = true,
                context = this
            )
        fragment.show(supportFragmentManager, "My Fragment")
    }

    private fun userCreatedError() {
        val fragment = PopUpCadastroCuidadorActivity(isError = true, context = this)
        fragment.show(supportFragmentManager, "My Fragment")
    }

    private fun userCreated() {
        val fragment = PopUpCadastroCuidadorActivity(isError = false, context = this)
        fragment.show(supportFragmentManager, "My Fragment")
    }

    fun cadastrar() {
        if (validateFields()) {
            if (isNewUser) {
                val cuidador = Cuidador(
                    nomeCuidador = binding.editTextNome.editText?.text.toString(),
                    cpfCuidador = binding.editTextCpf.text.toString().replace("-", "")
                        .replace(".", ""),
                    relacaoCuidador = binding.editTextNomeMae.editText?.text.toString(),
                    emailCuidador = binding.editTextEmail.editText?.text.toString(),
                    senhaCuidador = binding.editTextSenha.editText?.text.toString()
                )
                viewModel.cadastrarPaciente(cuidador)
            } else {
                val cuidador = Cuidador(
                    nomeCuidador = binding.editTextNome.editText?.text.toString(),
                    cpfCuidador = binding.editTextCpf.text.toString().replace("-", "")
                        .replace(".", ""),
                    relacaoCuidador = binding.editTextNomeMae.editText?.text.toString(),
                    emailCuidador = binding.editTextEmail.editText?.text.toString(),
                    senhaCuidador = binding.editTextSenha.editText?.text.toString()
                )
                viewModel.editar(cuidador)
            }
        }
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
        return true
    }
}