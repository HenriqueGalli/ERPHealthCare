package com.project.erphealthcare.ui.paciente.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.erphealthcare.R
import com.project.erphealthcare.data.api.ApiService
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.GetPacienteResult
import com.project.erphealthcare.databinding.ActivityHomePacienteBinding
import com.project.erphealthcare.ui.paciente.SinalVital.SinaisVitaisActivity
import com.project.erphealthcare.ui.paciente.cadastro.CadastroPacienteActivity
import com.project.erphealthcare.ui.paciente.exames.ListaExamesActivity
import com.project.erphealthcare.ui.paciente.historicoMedico.HistoricoMedicoPacienteActivity


class HomePacienteActivity : AppCompatActivity() {

    private lateinit var paciente: Paciente
    private lateinit var token: String

    private lateinit var binding: ActivityHomePacienteBinding

    private var isCuidador = false

    private val viewModel: HomePacienteViewModel =
        HomePacienteViewModelFactory()
            .create(HomePacienteViewModel::class.java)

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_paciente)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_paciente)
        token = if (intent.hasExtra("TOKEN")) {
            intent.getStringExtra("TOKEN") ?: ""
        } else{
            ApiService.token
        }
        if (intent.hasExtra("VISAO_CUIDADOR_PACIENTE")) {
            isCuidador = true
            paciente = intent.getSerializableExtra("VISAO_CUIDADOR_PACIENTE", Paciente::class.java)!!
            setupPaciente(paciente)
        } else {
            getPaciente(token)
        }
        setupObserver()

    }

    private fun setubListeners(pac: Paciente, tok: String) {
        binding.imageViewUserLogo.setOnClickListener {
            val intent = Intent(this, CadastroPacienteActivity::class.java)
            intent.putExtra("PACIENTE", pac)
            if (isCuidador)
                intent.putExtra("VISAO_CUIDADOR", true)
            intent.putExtra("TOKEN", tok)
            startActivity(intent)
            this.finish()
        }
        binding.clAlergias.setOnClickListener {
            val intent = Intent(this, HistoricoMedicoPacienteActivity::class.java)
            intent.putExtra("TIPO_HISTORICO", "ALERGIAS")
            intent.putExtra("PACIENTE", pac)
            if (isCuidador)
                intent.putExtra("VISAO_CUIDADOR", pac.id)
            startActivity(intent)
            this.finish()
        }
        binding.clCirurgias.setOnClickListener {
            val intent = Intent(this, HistoricoMedicoPacienteActivity::class.java)
            intent.putExtra("TIPO_HISTORICO", "CIRURGIAS")
            intent.putExtra("PACIENTE", pac)
            if (isCuidador)
                intent.putExtra("VISAO_CUIDADOR", pac.id)
            startActivity(intent)
            this.finish()
        }
        binding.clDoencas.setOnClickListener {
            val intent = Intent(this, HistoricoMedicoPacienteActivity::class.java)
            intent.putExtra("TIPO_HISTORICO", "DOENCAS")
            intent.putExtra("PACIENTE", pac)
            if (isCuidador)
                intent.putExtra("VISAO_CUIDADOR", pac.id)
            startActivity(intent)
            this.finish()
        }
        binding.clMedicamentos.setOnClickListener {
            val intent = Intent(this, HistoricoMedicoPacienteActivity::class.java)
            intent.putExtra("TIPO_HISTORICO", "MEDICAMENTOS_ANTERIORES")
            intent.putExtra("PACIENTE", pac)
            if (isCuidador)
                intent.putExtra("VISAO_CUIDADOR", pac.id)
            startActivity(intent)
            this.finish()
        }
        binding.clMedicamentosAtuais.setOnClickListener {
            val intent = Intent(this, HistoricoMedicoPacienteActivity::class.java)
            intent.putExtra("TIPO_HISTORICO", "MEDICAMENTOS_ATUAIS")
            intent.putExtra("PACIENTE", pac)
            if (isCuidador)
                intent.putExtra("VISAO_CUIDADOR", pac.id)
            startActivity(intent)
            this.finish()
        }
        binding.clBatimentosMenu.setOnClickListener {
            val intent = Intent(this, SinaisVitaisActivity::class.java)
            intent.putExtra("MEDICAO", "BATIMENTOS")
            intent.putExtra("PACIENTE", pac)
            if (isCuidador)
                intent.putExtra("VISAO_CUIDADOR", pac.id)
            startActivity(intent)
            this.finish()
        }
        binding.clOxigenacao.setOnClickListener {
            val intent = Intent(this, SinaisVitaisActivity::class.java)
            intent.putExtra("MEDICAO", "OXIGENACAO")
            intent.putExtra("PACIENTE", pac)
            if (isCuidador)
                intent.putExtra("VISAO_CUIDADOR", pac.id)
            startActivity(intent)
            this.finish()
        }
        binding.clTemperatura.setOnClickListener {
            val intent = Intent(this, SinaisVitaisActivity::class.java)
            intent.putExtra("MEDICAO", "TEMPERATURA")
            intent.putExtra("PACIENTE", pac)
            if (isCuidador)
                intent.putExtra("VISAO_CUIDADOR", pac.id)
            startActivity(intent)
            this.finish()
        }
        binding.clExames.setOnClickListener {
            val intent = Intent(this, ListaExamesActivity::class.java)
            startActivity(intent)
            intent.putExtra("PACIENTE", pac)
            if (isCuidador)
                intent.putExtra("VISAO_CUIDADOR", pac.id)
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
        if (user != null) {
            setubListeners(user, token)
        }
        viewModel.validateMedicalHistory()
        if (user != null) {
            paciente = user
            val nome = paciente.nome
            binding.labelAcompanhamentoPaciente.text =
                "Olá $nome! Aqui estão suas informações para acompanhamento!"
        }
    }

    private fun setupPaciente(user: Paciente?) {
        if (user != null) {
            setubListeners(user, token)
            paciente = user
            val nome = paciente.nome
            binding.labelAcompanhamentoPaciente.text =
                "Informações do paciente: $nome"
        }
    }

    private fun getPaciente(token: String) {
        viewModel.getPaciente(token)
    }
}