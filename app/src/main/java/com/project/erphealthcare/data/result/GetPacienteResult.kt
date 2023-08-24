package com.project.erphealthcare.data.result

import com.project.erphealthcare.data.model.Paciente

sealed class GetPacienteResult {
    data class Success(val user: Paciente?) : GetPacienteResult()
    object ServerError : GetPacienteResult()
}