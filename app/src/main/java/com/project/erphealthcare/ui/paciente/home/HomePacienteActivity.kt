package com.project.erphealthcare.ui.paciente.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.GetPacienteResult
import com.project.erphealthcare.databinding.ActivityCadastroPacienteBinding
import com.project.erphealthcare.databinding.ActivityHomePacienteBinding
import com.project.erphealthcare.ui.paciente.alergias.AlergiasPacienteActivity
import com.project.erphealthcare.ui.paciente.batimentos.BatimentosCardiacosActivity
import com.project.erphealthcare.ui.paciente.cadastro.CadastroPacienteActivity
import com.project.erphealthcare.ui.paciente.cadastro.CadastroPacienteViewModel
import com.project.erphealthcare.ui.paciente.cadastro.CadastroPacienteViewModelFactory
import com.project.erphealthcare.ui.paciente.exames.ListaExamesActivity

class HomePacienteActivity : AppCompatActivity() {

    private lateinit var paciente: Paciente

    private lateinit var binding: ActivityHomePacienteBinding

    private val viewModel: HomePacienteViewModel =
        HomePacienteViewModelFactory()
            .create(HomePacienteViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_paciente)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_paciente)
        setupObserver()
        setubListeners()
        if (intent.hasExtra("TOKEN")) {
            getPaciente(intent.getStringExtra("TOKEN") ?: "")
        }


    }

    private fun setubListeners() {
        binding.imageViewUserLogo.setOnClickListener {
            val intent = Intent(this, CadastroPacienteActivity::class.java)
            intent.putExtra("PACIENTE", paciente)
            startActivity(intent)
            this.finish()
        }
        binding.clAlergias.setOnClickListener {
            val intent = Intent(this, AlergiasPacienteActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        binding.clBatimentosMenu.setOnClickListener{
            val intent = Intent(this, BatimentosCardiacosActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        binding.clExames.setOnClickListener{
            val intent = Intent(this, ListaExamesActivity::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    private fun setupObserver() {
        viewModel.pacienteLiveData.observe(this) { res ->
            when (res) {
                is GetPacienteResult.Success -> setupUser(res.user)
                is GetPacienteResult.ServerError -> erroGetUser()
            }
        }
    }

    private fun erroGetUser() {

    }

    private fun setupUser(user: Paciente?) {
        viewModel.validateMedicalHistory()
        if (user != null) {
            paciente = user
            val nome = paciente.nome
            binding.labelAcompanhamentoPaciente.text =
                "Olá $nome! Aqui estão suas informações para acompanhamento!"
        }
    }

    private fun getPaciente(token: String) {
        viewModel.getPaciente(token)
    }
}