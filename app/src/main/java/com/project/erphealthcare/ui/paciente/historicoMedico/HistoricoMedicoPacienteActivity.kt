package com.project.erphealthcare.ui.paciente.historicoMedico

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.erphealthcare.R
import com.project.erphealthcare.data.api.ApiService
import com.project.erphealthcare.data.model.HistoricoMedico
import com.project.erphealthcare.data.result.GetMedicalHistoryResult
import com.project.erphealthcare.data.result.UpdateMedicalHistoryResult
import com.project.erphealthcare.databinding.ActivityAlergiasPacienteBinding
import com.project.erphealthcare.ui.paciente.home.HomePacienteActivity

class HistoricoMedicoPacienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlergiasPacienteBinding

    private val viewModel: HistoricoMedicoPacienteViewModel =
        HistoricoMedicoPacienteViewModelFactory()
            .create(HistoricoMedicoPacienteViewModel::class.java)

    private lateinit var adapter: ListagemAdapter

    private lateinit var historicoMedico: HistoricoMedico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAlergiasPacienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("TIPO_HISTORICO")) {
            when (intent.getStringExtra("TIPO_HISTORICO")) {
                "ALERGIAS" -> setupAlergiasView()
                "CIRURGIAS" -> setupCirurgiasView()
                "DOENCAS" -> setupDoencasView()
                "MEDICAMENTOS_ANTERIORES" -> setupMedicamentosAnterioresView()
                "MEDICAMENTOS_ATUAIS" -> setupMedicamentosAtuaisView()
            }
            viewModel.getMedicalHistory()
        }


    }

    private fun setupMedicamentosAnterioresView() {
        setupMedicamentosAnterioresLayout()
        setupMedicamentosAnterioresListeners()
        setupMedicamentosAnterioresObservers()
    }

    private fun setupMedicamentosAtuaisView() {
        setupMedicamentosAtuaisLayout()
        setupMedicamentosAtuaisListeners()
        setupMedicamentosAtuaisObservers()
    }

    private fun setupAlergiasView() {
        setupAlergiasLayout()
        setupAlergiaListeners()
        setupAlergiaObservers()
    }

    private fun setupDoencasView() {
        setupDoencasLayout()
        setupDoencasListeners()
        setupDoencasObservers()
    }

    private fun setupCirurgiasView() {
        setupCirurgiasLayout()
        setupCirurgiasListeners()
        setupCirurgiasObservers()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomePacienteActivity::class.java)
        intent.putExtra("TOKEN", ApiService.token)
        startActivity(intent)
        this.finish()
    }

    private fun setupMedicamentosAtuaisObservers() {
        viewModel.medicalHistoryResult.observe(this) { res ->
            when (res) {
                is GetMedicalHistoryResult.ServerError -> errorGetHistory()
                is GetMedicalHistoryResult.Success -> successMedicamentosAtuaisMedicalHistory(
                    res.historico
                )
            }
        }

        viewModel.updateAlergiasResult.observe(this) { res ->
            when (res) {
                is UpdateMedicalHistoryResult.ServerError -> errorGetHistory()
                is UpdateMedicalHistoryResult.Success -> successUpdateHistory()
            }
        }
    }

    private fun setupMedicamentosAnterioresObservers() {
        viewModel.medicalHistoryResult.observe(this) { res ->
            when (res) {
                is GetMedicalHistoryResult.ServerError -> errorGetHistory()
                is GetMedicalHistoryResult.Success -> successMedicamentosAnterioresMedicalHistory(
                    res.historico
                )
            }
        }

        viewModel.updateAlergiasResult.observe(this) { res ->
            when (res) {
                is UpdateMedicalHistoryResult.ServerError -> errorGetHistory()
                is UpdateMedicalHistoryResult.Success -> successUpdateHistory()
            }
        }
    }

    private fun setupAlergiaObservers() {
        viewModel.medicalHistoryResult.observe(this) { res ->
            when (res) {
                is GetMedicalHistoryResult.ServerError -> errorGetHistory()
                is GetMedicalHistoryResult.Success -> successAlergiasMedicalHistory(res.historico)
            }
        }

        viewModel.updateAlergiasResult.observe(this) { res ->
            when (res) {
                is UpdateMedicalHistoryResult.ServerError -> errorGetHistory()
                is UpdateMedicalHistoryResult.Success -> successUpdateHistory()
            }
        }
    }

    private fun setupCirurgiasObservers() {
        viewModel.medicalHistoryResult.observe(this) { res ->
            when (res) {
                is GetMedicalHistoryResult.ServerError -> errorGetHistory()
                is GetMedicalHistoryResult.Success -> successCirurgiasMedicalHistory(res.historico)
            }
        }

        viewModel.updateAlergiasResult.observe(this) { res ->
            when (res) {
                is UpdateMedicalHistoryResult.ServerError -> errorGetHistory()
                is UpdateMedicalHistoryResult.Success -> successUpdateHistory()
            }

        }
    }

    private fun setupDoencasObservers() {
        viewModel.medicalHistoryResult.observe(this) { res ->
            when (res) {
                is GetMedicalHistoryResult.ServerError -> errorGetHistory()
                is GetMedicalHistoryResult.Success -> successDoencasMedicalHistory(res.historico)
            }
        }

        viewModel.updateAlergiasResult.observe(this) { res ->
            when (res) {
                is UpdateMedicalHistoryResult.ServerError -> errorGetHistory()
                is UpdateMedicalHistoryResult.Success -> successUpdateHistory()
            }

        }
    }

    private fun successUpdateHistory() {
        onBackPressed()
    }

    private fun successAlergiasMedicalHistory(historico: HistoricoMedico?) {
        if (historico != null) {
            historicoMedico = historico
        }
        setupAdapter(historico?.alergias ?: ArrayList())
    }

    private fun successMedicamentosAtuaisMedicalHistory(historico: HistoricoMedico?) {
        if (historico != null) {
            historicoMedico = historico
        }
        setupAdapter(historico?.medicamentosAtuais ?: ArrayList())
    }

    private fun successMedicamentosAnterioresMedicalHistory(historico: HistoricoMedico?) {
        if (historico != null) {
            historicoMedico = historico
        }
        setupAdapter(historico?.medicamentosAnteriores ?: ArrayList())
    }

    private fun successDoencasMedicalHistory(historico: HistoricoMedico?) {
        if (historico != null) {
            historicoMedico = historico
        }
        setupAdapter(historico?.doencas ?: ArrayList())
    }

    private fun successCirurgiasMedicalHistory(historico: HistoricoMedico?) {
        if (historico != null) {
            historicoMedico = historico
        }
        setupAdapter(historico?.cirurgias ?: ArrayList())
    }

    private fun errorGetHistory() {
        TODO("Not yet implemented")
    }

    private fun setupAlergiasLayout() {
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.tvTitle.text = "Histórico de\nAlergias"
    }

    private fun setupMedicamentosAtuaisLayout() {
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.tvTitle.text = "Medicamentos\nAtuais"
    }

    private fun setupMedicamentosAnterioresLayout() {
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.tvTitle.text = "Histórico de\nMedicamentos"
    }

    private fun setupDoencasLayout() {
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.tvTitle.text = "Histórico de\nDoenças"
    }

    private fun setupCirurgiasLayout() {
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.tvTitle.text = "Histórico de\nCirurgias"
    }

    private fun setupAdapter(alergias: ArrayList<String>) {
        adapter = ListagemAdapter(alergias)
        binding.rvHistorico.adapter = adapter
        binding.rvHistorico.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupMedicamentosAtuaisListeners() {
        binding.addHistorico.setOnClickListener { view ->
            adapter.addItem()
            val itemCount = adapter.itemCount
            binding.rvHistorico.smoothScrollToPosition(itemCount - 1)
        }

        binding.btnSalvar.setOnClickListener {
            historicoMedico.medicamentosAtuais = adapter.historico
            viewModel.updateMedicalHistory(historicoMedico)
        }
    }

    private fun setupMedicamentosAnterioresListeners() {
        binding.addHistorico.setOnClickListener { view ->
            adapter.addItem()
            val itemCount = adapter.itemCount
            binding.rvHistorico.smoothScrollToPosition(itemCount - 1)
        }

        binding.btnSalvar.setOnClickListener {
            historicoMedico.medicamentosAnteriores = adapter.historico
            viewModel.updateMedicalHistory(historicoMedico)
        }
    }

    private fun setupAlergiaListeners() {
        binding.addHistorico.setOnClickListener { view ->
            adapter.addItem()
            val itemCount = adapter.itemCount
            binding.rvHistorico.smoothScrollToPosition(itemCount - 1)
        }

        binding.btnSalvar.setOnClickListener {
            historicoMedico.alergias = adapter.historico
            viewModel.updateMedicalHistory(historicoMedico)
        }
    }

    private fun setupCirurgiasListeners() {
        binding.addHistorico.setOnClickListener { view ->
            adapter.addItem()
            val itemCount = adapter.itemCount
            binding.rvHistorico.smoothScrollToPosition(itemCount - 1)
        }

        binding.btnSalvar.setOnClickListener {
            historicoMedico.cirurgias = adapter.historico
            viewModel.updateMedicalHistory(historicoMedico)
        }
    }

    private fun setupDoencasListeners() {
        binding.addHistorico.setOnClickListener { view ->
            adapter.addItem()
            val itemCount = adapter.itemCount
            binding.rvHistorico.smoothScrollToPosition(itemCount - 1)
        }

        binding.btnSalvar.setOnClickListener {
            historicoMedico.doencas = adapter.historico
            viewModel.updateMedicalHistory(historicoMedico)
        }
    }
}