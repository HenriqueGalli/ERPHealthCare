package com.project.erphealthcare.ui.paciente.historicoMedico

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.HistoricoMedico
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.GetMedicalHistoryResult
import com.project.erphealthcare.data.result.UpdateMedicalHistoryResult
import com.project.erphealthcare.databinding.ActivityAlergiasPacienteBinding
import com.project.erphealthcare.ui.paciente.home.HomePacienteActivity

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
class HistoricoMedicoPacienteActivity : AppCompatActivity(), onAddHistorico {

    private lateinit var binding: ActivityAlergiasPacienteBinding

    private val viewModel: HistoricoMedicoPacienteViewModel =
        HistoricoMedicoPacienteViewModelFactory()
            .create(HistoricoMedicoPacienteViewModel::class.java)

    private lateinit var adapter: ListagemAdapter

    private lateinit var historicoMedico: HistoricoMedico

    private var isCuidador = false
    private var idPaciente = 0

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
            if (intent.hasExtra("VISAO_CUIDADOR")) {
                isCuidador = true
                idPaciente = intent.getIntExtra("VISAO_CUIDADOR", 0)
                viewModel.getMedicalHistoryCuidador(idPaciente)
            } else {
                isCuidador = false
                viewModel.getMedicalHistory()
            }

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
        if (this.intent.hasExtra("VISAO_CUIDADOR")) {
            intent.putExtra(
                "VISAO_CUIDADOR_PACIENTE",
                this.intent.getSerializableExtra("PACIENTE", Paciente::class.java)
            )
        } else if (this.intent.hasExtra("PACIENTE")) {
            intent.putExtra(
                "PACIENTE", this.intent.getSerializableExtra("PACIENTE", Paciente::class.java)
            )
        }
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

                else -> errorGetHistory()
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

                else -> {
                    errorGetHistory()
                }
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
                else -> {
                    errorGetHistory()
                }
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
                else -> {
                    errorGetHistory()
                }
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
                else -> {
                    errorGetHistory()
                }
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
        adapter = ListagemAdapter(alergias, this)
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
            binding.addHistorico.visibility = View.INVISIBLE
        }

        binding.btnSalvar.setOnClickListener {
            historicoMedico.medicamentosAtuais = adapter.historico
            if (isCuidador) {
                viewModel.updateMedicalHistoryCuidador(idPaciente, historicoMedico)
            } else {
                viewModel.updateMedicalHistory(historicoMedico)
            }
        }
    }

    private fun setupMedicamentosAnterioresListeners() {
        binding.addHistorico.setOnClickListener { view ->
            adapter.addItem()
            val itemCount = adapter.itemCount
            binding.rvHistorico.smoothScrollToPosition(itemCount - 1)
            binding.addHistorico.visibility = View.INVISIBLE

        }

        binding.btnSalvar.setOnClickListener {
            historicoMedico.medicamentosAnteriores = adapter.historico
            if (isCuidador) {
                viewModel.updateMedicalHistoryCuidador(idPaciente, historicoMedico)
            } else {
                viewModel.updateMedicalHistory(historicoMedico)
            }

        }
    }

    private fun setupAlergiaListeners() {
        binding.addHistorico.setOnClickListener { view ->
            adapter.addItem()
            val itemCount = adapter.itemCount
            binding.rvHistorico.smoothScrollToPosition(itemCount - 1)
            binding.addHistorico.visibility = View.INVISIBLE
        }

        binding.btnSalvar.setOnClickListener {
            historicoMedico.alergias = adapter.historico
            if (isCuidador) {
                viewModel.updateMedicalHistoryCuidador(idPaciente, historicoMedico)
            } else {
                viewModel.updateMedicalHistory(historicoMedico)
            }
        }
    }

    private fun setupCirurgiasListeners() {
        binding.addHistorico.setOnClickListener { view ->
            adapter.addItem()
            val itemCount = adapter.itemCount
            binding.rvHistorico.smoothScrollToPosition(itemCount - 1)
            binding.addHistorico.visibility = View.INVISIBLE

        }

        binding.btnSalvar.setOnClickListener {
            historicoMedico.cirurgias = adapter.historico
            if (isCuidador) {
                viewModel.updateMedicalHistoryCuidador(idPaciente, historicoMedico)
            } else {
                viewModel.updateMedicalHistory(historicoMedico)
            }
        }
    }

    private fun setupDoencasListeners() {
        binding.addHistorico.setOnClickListener { view ->
            adapter.addItem()
            val itemCount = adapter.itemCount
            binding.rvHistorico.smoothScrollToPosition(itemCount - 1)
            binding.addHistorico.visibility = View.INVISIBLE

        }

        binding.btnSalvar.setOnClickListener {
            historicoMedico.doencas = adapter.historico
            if (isCuidador) {
                viewModel.updateMedicalHistoryCuidador(idPaciente, historicoMedico)
            } else {
                viewModel.updateMedicalHistory(historicoMedico)
            }
        }
    }

    override fun setVisible() {
        binding.addHistorico.visibility = View.VISIBLE
    }

    override fun setInvisible() {
        binding.addHistorico.visibility = View.INVISIBLE
    }
}