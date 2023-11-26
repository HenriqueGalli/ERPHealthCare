package com.project.erphealthcare.data.result

import com.project.erphealthcare.data.model.Agendamento

sealed class GetCalendarioResult {
    data class Success(val agendamento: ArrayList<Agendamento>) : GetCalendarioResult()
    object ServerError : GetCalendarioResult()
}