package com.project.erphealthcare.data.result

import com.project.erphealthcare.data.model.HistoricoMedico

sealed class GetMedicalHistoryResult {
    data class Success(val historico: HistoricoMedico?) : GetMedicalHistoryResult()
    object ServerError : GetMedicalHistoryResult()
}