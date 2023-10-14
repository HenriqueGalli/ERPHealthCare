package com.project.erphealthcare.data.result

import com.project.erphealthcare.data.model.Paciente

sealed class GetListaPacienteResult {
    data class Success(val pacientesLista: ArrayList<Paciente>?) : GetListaPacienteResult()
    object ServerError : GetListaPacienteResult()
}