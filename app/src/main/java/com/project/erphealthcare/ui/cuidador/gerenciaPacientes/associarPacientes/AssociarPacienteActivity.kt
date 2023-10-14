package com.project.erphealthcare.ui.cuidador.gerenciaPacientes.associarPacientes.associarPaciente

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.erphealthcare.R
import com.project.erphealthcare.data.result.AssociateCaregiverUserResult
import com.project.erphealthcare.databinding.ActivityAssociarPacienteBinding
import com.project.erphealthcare.utils.CpfUtils

class AssociarPacienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAssociarPacienteBinding

    private lateinit var token: String

    private val viewModel: AssociarPacienteViewModel =
        AssociarPacienteViewModelFactory().create(AssociarPacienteViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_associar_paciente)
        if (intent.hasExtra("TOKEN")) token = intent.getStringExtra("TOKEN") ?: ""
        setupListener()
        setupObserver()
    }

    private fun validateFields(): Boolean {
        if (binding.etEmail.editText?.text.toString().isEmpty()) {
            binding.etEmail.error = "Campo Obrigatório"
            return false
        }
        if (binding.editTextCpf.text.toString().isEmpty()) {
            binding.editTextCpf.error = "Campo Obrigatório"
            return false
        }
        return true
    }

    private fun setupObserver() {
        viewModel.associarLiveData.observe(this) { res ->
            when (res) {
                is AssociateCaregiverUserResult.Success -> userAssociated()
                else -> userAssociatedError()
            }
        }
    }

    private fun userAssociated() {
        val fragment = PopUpAssociarPacienteActivity(isError = false, token = token)
        fragment.show(supportFragmentManager, "My Fragment")
    }

    private fun userAssociatedError() {
        val fragment = PopUpAssociarPacienteActivity(isError = true, token = token)
        fragment.show(supportFragmentManager, "My Fragment")
    }

    private fun setupListener() {
        binding.btnAssociar.setOnClickListener {
            if (validateFields()) {
                viewModel.associarPaciente(
                    binding.etEmail.editText?.text.toString(),
                    binding.editTextCpf.text.toString().replace("-", "").replace(".", "")
                )
            }
        }
        binding.editTextCpf.addTextChangedListener(
            CpfUtils.mask(
                "###.###.###-##", binding.editTextCpf
            )
        )
    }
}