package com.project.erphealthcare.ui.paciente.SinalVital

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.project.erphealthcare.R
import com.project.erphealthcare.data.api.ApiService
import com.project.erphealthcare.data.model.MedicoesSinaisVitais
import com.project.erphealthcare.data.result.GetSinaisVitaisResult
import com.project.erphealthcare.databinding.ActivityBatimentosCardiacosBinding
import com.project.erphealthcare.ui.paciente.home.HomePacienteActivity
import com.project.erphealthcare.utils.CpfUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SinaisVitaisActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBatimentosCardiacosBinding
    private var tipoMedicao = ""
    private val viewModel: SinaisPacienteViewModel =
        SinaisPacienteViewModelFactory()
            .create(SinaisPacienteViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_batimentos_cardiacos)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_batimentos_cardiacos)
        setupObserver()
        setupListener()
        if (intent.hasExtra("MEDICAO")) {
            tipoMedicao = intent.getStringExtra("MEDICAO").toString()
            validateTipoMedicao(tipoMedicao,obterDataAtualNoFormato())
        }

    }

    private fun validateTipoMedicao(tipoMedicao: String, dataMedicao: String) {
        when (tipoMedicao) {
            "BATIMENTOS" -> viewModel.getBatimentosCardiacos(dataMedicao)
            "OXIGENACAO" -> viewModel.getOxigenacaoSanguinea(dataMedicao)
            "TEMPERATURA" -> viewModel.getTemperaturaCorporal(dataMedicao)
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

    private fun obterDataAtualNoFormato(): String {
        val formato = SimpleDateFormat("yyyy-MM-dd")
        val dataAtual = Date()
        return formato.format(dataAtual)
    }

    private fun setupError() {
        binding.textViewMensagem.visibility = View.INVISIBLE
    }

    private fun setupAdapter(listaMedicoes: MutableList<MedicoesSinaisVitais>) {
        // Limpar os dados existentes no gráfico
        binding.lineChart.clear()
        binding.textViewMensagem.visibility = View.INVISIBLE

        if(listaMedicoes.size == 0)
            binding.textViewMensagem.visibility = View.VISIBLE

        when (tipoMedicao) {
            "BATIMENTOS" -> binding.textViewTitulo.text = "Acompanhamento\nBatimentos Cardiácos"
            "OXIGENACAO" -> binding.textViewTitulo.text = "Acompanhamento\nOxigenação Sanguínea"
            "TEMPERATURA" -> binding.textViewTitulo.text = "Acompanhamento\nTemperatura Corporal"
        }

        val entries: MutableList<Entry> = ArrayList()
        val dateFormatInput = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val dateFormatOutput = SimpleDateFormat("HH:mm", Locale.getDefault())

        for ((index, medicao) in listaMedicoes.withIndex()) {
            medicao.dateTimeMedicao?.let {
                val date = dateFormatInput.parse(it)
                val formattedDate = date?.let { it1 -> dateFormatOutput.format(it1) }

                medicao.valorMedicao?.toFloat()?.let {
                    entries.add(Entry(index.toFloat(), it, formattedDate))
                }
            }
        }

        val lineDataSet = LineDataSet(entries, "Sinais Vitais")
        lineDataSet.color = Color.rgb(62, 125, 255)
        lineDataSet.circleRadius = 8f
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillColor = Color.rgb(62, 125, 255)
        lineDataSet.valueTextColor = Color.rgb(62, 125, 255)
        lineDataSet.fillAlpha = 100

        val lineData = LineData(lineDataSet)
        binding.lineChart.data = lineData
        binding.lineChart.setTouchEnabled(false)
        binding.lineChart.setPinchZoom(false)
        val dataSets: MutableList<ILineDataSet> = ArrayList()
        dataSets.add(lineDataSet)

        val description = Description()
        description.text = ""
        binding.lineChart.description = description

        val xAxis: XAxis = binding.lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false) // Remove as linhas de grade do eixo X

        // Formatar os valores do eixo X como dia/mês/ano
        xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                if (index >= 0 && index < entries.size) {
                    return entries[index].data.toString()
                }
                return "" // Retorna uma string vazia se o índice estiver fora dos limites
            }
        }

        binding.lineChart.invalidate()
    }


    private fun getDate(): String {
        val year = binding.simpleDatePicker.year
        val month = setupMonth()
        val day = setupDay()
        return "$year-$month-$day"
    }
    private fun setupDay(): String {
        val day = binding.simpleDatePicker.dayOfMonth
        return if (day.toString().length == 2)
            day.toString()
        else "0$day"
    }

    private fun setupMonth(): String {
        val year = binding.simpleDatePicker.month + 1
        return if (year.toString().length == 2)
            year.toString()
        else "0$year"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomePacienteActivity::class.java)
        intent.putExtra("TOKEN", ApiService.token)
        startActivity(intent)
        this.finish()
    }

    private fun setupListener() {
        binding.btnBuscarExame.setOnClickListener {
            val dataMedicao = getDate()
            validateTipoMedicao(tipoMedicao ,dataMedicao)
        }
    }
}
