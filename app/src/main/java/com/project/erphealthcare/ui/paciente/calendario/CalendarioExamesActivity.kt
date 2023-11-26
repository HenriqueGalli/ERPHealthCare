package com.project.erphealthcare.ui.paciente.calendario

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.applikeysolutions.cosmocalendar.view.CalendarView
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.Agendamento
import com.project.erphealthcare.data.result.GetCalendarioResult
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

class CalendarioExamesActivity : AppCompatActivity() {

    private val viewModel: CalendarioViewModel =
        CalendarioViewModelFactory()
            .create(CalendarioViewModel::class.java)

    private var calendarView: CalendarView? = findViewById(R.id.calendarView)
    private var markedDates: MutableList<Date?>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario_exames)
        markedDates = ArrayList()
        viewModel.getCalendario()
        setupObserver()
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
            markedDates!!.add(agendamento.dataAgendamento?.let { parseStringToDate(it) })
        }
        calendarView?.calendarOrientation = 0
//        calendarView.setCalendarListener(object : CalendarListener() {
//            fun onDayClick(day: Day) {
//                // Lógica para lidar com o clique em uma data
//                Toast.makeText(
//                    this@CalendarioActivity,
//                    "Data clicada: " + day.toString(),
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        })
        // calendarView.
    }

    private fun parseStringToDate(dateString: String): Date? {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        return try {
            format.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
            null
        }
    }
}