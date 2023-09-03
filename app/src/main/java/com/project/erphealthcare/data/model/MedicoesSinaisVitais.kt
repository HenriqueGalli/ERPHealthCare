package com.project.erphealthcare.data.model

import com.squareup.moshi.Json
import java.io.Serializable

data class MedicoesSinaisVitais(
    @Json(name = "dateTimeMedicao") var dateTimeMedicao: String?,
    @Json(name = "valorMedicao") var valorMedicao: Double?,
) : Serializable