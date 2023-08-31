package com.project.erphealthcare.data.result

import com.project.erphealthcare.data.model.HistoricoMedico

sealed class UpdateMedicalHistoryResult {
    data class Success(val historico: HistoricoMedico?) : UpdateMedicalHistoryResult()
    object ServerError : UpdateMedicalHistoryResult()
}