package com.project.erphealthcare.ui.paciente.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.GetPacienteResult
import com.project.erphealthcare.databinding.ActivityHomePacienteBinding
import com.project.erphealthcare.ui.paciente.SinalVital.BatimentosCardiacosActivity
import com.project.erphealthcare.ui.paciente.historicoMedico.HistoricoMedicoPacienteActivity
import com.project.erphealthcare.ui.paciente.cadastro.CadastroPacienteActivity
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
            val intent = Intent(this, HistoricoMedicoPacienteActivity::class.java)
            intent.putExtra("TIPO_HISTORICO", "ALERGIAS")
            startActivity(intent)
            this.finish()
        }
        binding.clCirurgias.setOnClickListener {
            val intent = Intent(this, HistoricoMedicoPacienteActivity::class.java)
            intent.putExtra("TIPO_HISTORICO", "CIRURGIAS")
            startActivity(intent)
            this.finish()
        }
        binding.clDoencas.setOnClickListener {
            val intent = Intent(this, HistoricoMedicoPacienteActivity::class.java)
            intent.putExtra("TIPO_HISTORICO", "DOENCAS")
            startActivity(intent)
            this.finish()
        }
        binding.clMedicamentos.setOnClickListener {
            val intent = Intent(this, HistoricoMedicoPacienteActivity::class.java)
            intent.putExtra("TIPO_HISTORICO", "MEDICAMENTOS_ANTERIORES")
            startActivity(intent)
            this.finish()
        }
        binding.clMedicamentosAtuais.setOnClickListener {
            val intent = Intent(this, HistoricoMedicoPacienteActivity::class.java)
            intent.putExtra("TIPO_HISTORICO", "MEDICAMENTOS_ATUAIS")
            startActivity(intent)
            this.finish()
        }
        binding.clBatimentosMenu.setOnClickListener{
            val intent = Intent(this, BatimentosCardiacosActivity::class.java)
            intent.putExtra("MEDICAO", "BATIMENTOS")
            startActivity(intent)
            this.finish()
        }
        binding.clOxigenacao.setOnClickListener{
            val intent = Intent(this, BatimentosCardiacosActivity::class.java)
            intent.putExtra("MEDICAO", "OXIGENACAO")
            startActivity(intent)
            this.finish()
        }
        binding.clTemperatura.setOnClickListener{
            val intent = Intent(this, BatimentosCardiacosActivity::class.java)
            intent.putExtra("MEDICAO", "TEMPERATURA")
            startActivity(intent)
            this.finish()
        }
        binding.clExames.setOnClickListener {
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