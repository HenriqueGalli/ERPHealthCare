package com.project.erphealthcare.ui.paciente.batimentos

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.project.erphealthcare.R
import com.project.erphealthcare.data.api.ApiService
import com.project.erphealthcare.data.model.MedicoesSinaisVitais
import com.project.erphealthcare.data.result.GetSinaisVitaisResult
import com.project.erphealthcare.databinding.ActivityBatimentosCardiacosBinding
import com.project.erphealthcare.ui.paciente.home.HomePacienteActivity
import com.project.erphealthcare.utils.Formater

class BatimentosCardiacosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBatimentosCardiacosBinding

    private val viewModel: BatimentosPacienteViewModel =
        BatimentosPacienteViewModelFactory()
            .create(BatimentosPacienteViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_batimentos_cardiacos)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_batimentos_cardiacos)

        setupObserver()
        viewModel.getBatimentosCardiacos()
    }

    private fun setupObserver() {
        viewModel.batimentosResult.observe(this) { res ->

            when (res) {
                is GetSinaisVitaisResult.Success -> setupAdapter(res.medicoes)
                is GetSinaisVitaisResult.ServerError -> setupError()
            }
        }
    }

    private fun setupError() {

    }

    private fun setupAdapter(listaMedicoes: MutableList<MedicoesSinaisVitais>) {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaMedicoes.map { medicao ->
                "Medição: ${medicao.valorMedicao} Batimentos Cardíacos\nData da Medição: ${
                    Formater.formatarDataEHora(medicao.dateTimeMedicao ?: "")
                }"
            }
        )
        binding.listViewBatimentos.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomePacienteActivity::class.java)
        intent.putExtra("TOKEN", ApiService.token)
        startActivity(intent)
        this.finish()
    }
}
