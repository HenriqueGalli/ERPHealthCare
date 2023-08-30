package com.project.erphealthcare.ui.paciente.alergias

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.project.erphealthcare.R
import com.project.erphealthcare.data.api.ApiService
import com.project.erphealthcare.data.model.HistoricoMedico
import com.project.erphealthcare.data.result.GetMedicalHistoryResult
import com.project.erphealthcare.databinding.ActivityAlergiasPacienteBinding
import com.project.erphealthcare.ui.paciente.home.HomePacienteActivity

class AlergiasPacienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlergiasPacienteBinding

    private val viewModel: AlergiasPacienteViewModel =
        AlergiasPacienteViewModelFactory()
            .create(AlergiasPacienteViewModel::class.java)

    private lateinit var adapter: ListagemAdapter

    private lateinit var historicoMedico: HistoricoMedico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAlergiasPacienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupLayout()
        setupListeners()
        setupObservers()
        viewModel.getMedicalHistory()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomePacienteActivity::class.java)
        intent.putExtra("TOKEN", ApiService.token)
        startActivity(intent)
        this.finish()
    }

    private fun setupObservers() {
        viewModel.result.observe(this) { res ->
            when (res) {
                is GetMedicalHistoryResult.ServerError -> errorGetHistory()
                is GetMedicalHistoryResult.Success -> successMedicalHistory(res.historico)
            }
        }
    }

    private fun successMedicalHistory(historico: HistoricoMedico?) {
        if (historico != null) {
            historicoMedico = historico
        }
        setupAdapter(historico?.alergias)
    }

    private fun errorGetHistory() {
        TODO("Not yet implemented")
    }

    private fun setupLayout() {
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
    }

    private fun setupAdapter(alergias: ArrayList<String>?) {
        adapter = alergias?.let { ListagemAdapter(it) }!!
        binding.rvAlergias.adapter = adapter
        binding.rvAlergias.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL,
            false
        )

    }

    private fun setupListeners() {
        binding.addAlergia.setOnClickListener { view ->

        }
    }
}