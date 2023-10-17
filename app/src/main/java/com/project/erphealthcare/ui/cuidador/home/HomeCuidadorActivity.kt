package com.project.erphealthcare.ui.cuidador.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.Cuidador
import com.project.erphealthcare.data.result.GetCuidadorResult
import com.project.erphealthcare.databinding.ActivityHomeCuidadorBinding
import com.project.erphealthcare.ui.cuidador.gerenciaPacientes.associarPacientes.associarPaciente.AssociarPacienteActivity
import com.project.erphealthcare.ui.cuidador.cadastro.CreateCuidadorActivity
import com.project.erphealthcare.ui.cuidador.gerenciaPacientes.listaPacientes.ListarPacientesActivity

class HomeCuidadorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeCuidadorBinding
    private lateinit var cuidador: Cuidador

    private var token = ""

    private val viewModel: HomeCuidadorViewModel =
        HomeCuidadorViewModelFactory()
            .create(HomeCuidadorViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_cuidador)

        setupObserver()
        setupListeners()
        if (intent.hasExtra("TOKEN")) {
            token = intent.getStringExtra("TOKEN") ?: ""
            getCuidador(token)
        }

    }

    private fun setupListeners() {
        binding.clListarPacientes.setOnClickListener {
            val intent = Intent(this, ListarPacientesActivity::class.java)
            intent.putExtra("TOKEN", token)
            startActivity(intent)
        }
        binding.clAddPacientes.setOnClickListener {
            val intent = Intent(this, AssociarPacienteActivity::class.java)
            intent.putExtra("TOKEN", token)
            startActivity(intent)
        }
        binding.clRemovePaciente.setOnClickListener {
            val intent = Intent(this, ListarPacientesActivity::class.java)
            intent.putExtra("TOKEN", token)
            intent.putExtra("EDITAR_PACIENTE", true)
            startActivity(intent)
        }
        binding.imageViewUserLogo.setOnClickListener {
            val intent = Intent(this, CreateCuidadorActivity::class.java)
            intent.putExtra("CUIDADOR", cuidador)
            intent.putExtra("TOKEN", token)
            startActivity(intent)
            this.finish()
        }
    }

    private fun setupObserver() {
        viewModel.cuidadorLiveData.observe(this) { res ->
            when (res) {
                is GetCuidadorResult.Success -> setupCuidador(res.user)
                is GetCuidadorResult.ServerError -> erroGetCuidador()
                else -> erroGetCuidador()
            }
        }
    }

    private fun setupCuidador(user: Cuidador?) {

        if (user != null) {
            cuidador = user
            binding.labelHomeCuidador.text =
                "Olá ${user.nomeCuidador ?: "Cuidador"}! Realize aqui o gerenciamento e acompanhamento dos seus pacientes!"
        }
    }

    private fun erroGetCuidador() {

    }

    private fun getCuidador(token: String) {
        viewModel.getCuidador(token)
    }
}
