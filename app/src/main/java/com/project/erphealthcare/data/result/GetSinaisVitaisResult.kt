package com.project.erphealthcare.data.result

import com.project.erphealthcare.data.model.MedicoesSinaisVitais

sealed class GetSinaisVitaisResult {
    data class Success(val medicoes: ArrayList<MedicoesSinaisVitais>) : GetSinaisVitaisResult()
    object ServerError : GetSinaisVitaisResult()
}