package com.project.erphealthcare.ui.paciente.agenda

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.domain.Event
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.Agendamento
import com.project.erphealthcare.data.result.GetCalendarioResult
import com.project.erphealthcare.ui.paciente.cadastrarAgendamento.CadastrarAgendamentoActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class AgendaActivity : AppCompatActivity() {
    private lateinit var compactCalendarView: CompactCalendarView
    private val viewModel: AgendaViewModel =
        AgendaViewModelFactory()
            .create(AgendaViewModel::class.java)
    private lateinit var btnCadastrar: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda_paciente)
        setupObserver()
        compactCalendarView = findViewById(R.id.compactcalendar_view)
        compactCalendarView.setLocale(TimeZone.getTimeZone("America/Sao_Paulo"), Locale.getDefault())

        btnCadastrar = findViewById(R.id.btnCadastrar)

        setupListeners()

        viewModel.getCalendario()

        // Adiciona o TextView do mês atual
        val textViewMonthLabel = findViewById<TextView>(R.id.textViewMonthLabel)

        // Configura o texto do mês atual
        val currentMonth = SimpleDateFormat("MMMM yyyy", Locale("pt", "BR")).format(Date())
        textViewMonthLabel.text = currentMonth


        // Define um listener para lidar com a seleção de data
        compactCalendarView.setListener(object : CompactCalendarView.CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date) {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
                val selectedDate = dateFormat.format(dateClicked)

                // Obtém todos os eventos para a data selecionada
                val events = compactCalendarView.getEvents(dateClicked)

                // Verifica se há eventos
                if (events.isNotEmpty()) {
                    // Exibe informações dos eventos
                    for (event in events) {
                        val eventText = event.data as String
                        showToast("Evento em $selectedDate: \n$eventText")
                    }
                }
            }

            override fun onMonthScroll(firstDayOfNewMonth: Date) {
                val newMonth = SimpleDateFormat("MMMM yyyy", Locale("pt", "BR")).format(firstDayOfNewMonth)
                textViewMonthLabel.text = newMonth
            }
        })
    }

    private fun setupListeners() {
        btnCadastrar.setOnClickListener {
            val intent = Intent(this, CadastrarAgendamentoActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
    private fun setupObserver() {
        viewModel.getCalendarioResult.observe(this) { res ->
            when (res) {
                is GetCalendarioResult.Success -> successGetAgenda(res.agendamento)
                is GetCalendarioResult.ServerError -> setupError()
                else -> {setupError()}
            }
        }
    }
    private fun setupError() {

    }
    private fun successGetAgenda(agendamentos: ArrayList<Agendamento>) {
        for (agendamento in agendamentos) {
            addEventToCalendar(agendamento)
        }

    }
    private fun addEventToCalendar(agendamento: Agendamento) {
        try {
            // Crie um evento com as informações do JSON
            val eventText = "Tipo: ${agendamento.tipoAgendamento}\nDescrição: ${agendamento.descricaoAgendamento}\nMédico: ${agendamento.nomeMedico}\n" +
                    "Localizacao: ${agendamento.localizacao}\n" +
                    "Observacoes: ${agendamento.observacoes}\n"
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
            val date = dateFormat.parse(agendamento.dataAgendamento)
            val event = Event(R.color.colorAccent, date!!.time, eventText)

            // Adicione o evento ao CompactCalendarView
            compactCalendarView.addEvent(event)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}