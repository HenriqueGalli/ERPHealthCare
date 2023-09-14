package com.project.erphealthcare.ui.paciente.SinalVital

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
    private var tipoMedicao = ""
    private val viewModel: BatimentosPacienteViewModel =
        BatimentosPacienteViewModelFactory()
            .create(BatimentosPacienteViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_batimentos_cardiacos)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_batimentos_cardiacos)

        setupObserver()
        if(intent.hasExtra("MEDICAO") ){
            tipoMedicao = intent.getStringExtra("MEDICAO").toString()
            if (tipoMedicao != null) {
                validateTipoMedicao(tipoMedicao)
            }
        }

    }

    private fun validateTipoMedicao(tipoMedicao: String){
        when(tipoMedicao){
            "BATIMENTOS" -> viewModel.getBatimentosCardiacos()
            "OXIGENACAO" -> viewModel.getOxigenacaoSanguinea()
            "TEMPERATURA" -> viewModel.getTemperaturaCorporal()
        }
    }

    private fun setupObserver() {
        viewModel.medicaoResult.observe(this) { res ->

            when (res) {
                is GetSinaisVitaisResult.Success -> setupAdapter(res.medicoes)
                is GetSinaisVitaisResult.ServerError -> setupError()
            }
        }
    }

    private fun setupError() {

    }

    private fun setupAdapter(listaMedicoes: MutableList<MedicoesSinaisVitais>) {
        var medicaoLabel = ""
        when(tipoMedicao){
            "BATIMENTOS" -> medicaoLabel = "Batimentos Cardíacos"
            "OXIGENACAO" -> medicaoLabel = "% de Oximetria"
            "TEMPERATURA" -> medicaoLabel = "°C"

        }
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            listaMedicoes.map { medicao ->
                "Medição: ${medicao.valorMedicao} ${medicaoLabel}\nData da Medição: ${
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