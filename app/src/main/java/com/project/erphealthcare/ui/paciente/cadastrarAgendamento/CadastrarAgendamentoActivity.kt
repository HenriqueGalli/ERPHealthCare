package com.project.erphealthcare.ui.paciente.cadastrarAgendamento

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.Agendamento
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.CreateAgendamentoResult
import com.project.erphealthcare.data.result.CreatePacienteResult
import com.project.erphealthcare.databinding.ActivityAgendaCadastroBinding
import com.project.erphealthcare.databinding.ActivityCadastrarAgendamentoBinding
import com.project.erphealthcare.ui.paciente.agenda.AgendaActivity
import com.project.erphealthcare.ui.paciente.cadastro.PopUpCadastroActivity

class CadastrarAgendamentoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAgendaCadastroBinding
    private var agendamento: Agendamento? = null

    private val viewModel: CadastrarAgendamentoViewModel =
        CadastrarAgendamentoViewModelFactory()
            .create(CadastrarAgendamentoViewModel::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_agenda_cadastro)
        binding.viewModel = this
        setupListener()
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.cadastrarLiveData.observe(this) { res ->
            when (res) {
                is CreateAgendamentoResult.Success -> userCreated()
                else -> userCreatedError()
            }
        }
    }

    private fun userCreatedError() {
        val fragment = PopUpCadastroActivity(isError = true, context = this, isAgenamento = true)
        fragment.show(supportFragmentManager, "My Fragment")
        val intent = Intent(this, AgendaActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    private fun userCreated() {
        val fragment = PopUpCadastroActivity(isError = false, context = this, isAgenamento = true)
        fragment.show(supportFragmentManager, "My Fragment")
        val intent = Intent(this, AgendaActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    private fun setupListener() {
        binding.buttonEnviar.setOnClickListener {
            cadastrar()
        }
    }

    fun cadastrar() {
        val agendamento = Agendamento(
            tipoAgendamento = binding.editTextTipo.editText?.text.toString(),
            descricaoAgendamento = binding.editDescricao.editText?.text.toString(),
            nomeMedico = binding.editTextMedico.editText?.text.toString(),
            localizacao = binding.editTextLocalizacao.editText?.text.toString(),
            telefoneMedico = binding.editTelefone.editText?.text.toString(),
            observacoes = binding.editObservacao.editText?.text.toString(),
            dataAgendamento = getDate()
        )
        viewModel.cadastrarAgendamento(agendamento)
    }

    private fun getDate(): String {
        val year = binding.simpleDatePicker.year
        val month = setupMonth()
        val day = setupDay()
        return "$year-$month-$day" + "T00:00:00"
    }

    private fun setupDay(): String {
        val day = binding.simpleDatePicker.dayOfMonth + 1
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
}