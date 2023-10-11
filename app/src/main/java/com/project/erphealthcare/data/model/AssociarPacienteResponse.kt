package com.project.erphealthcare.data.model

import com.squareup.moshi.Json

data class AssociarPacienteResponse(
    @Json(name = "campo") val campo: Boolean?,
    @Json(name = "erro") val erro: Boolean?,
)
