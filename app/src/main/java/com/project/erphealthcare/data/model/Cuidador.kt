package com.project.erphealthcare.data.model

import com.squareup.moshi.Json

data class Cuidador(
    @Json(name = "nomeCuidador") val nomeCuidador: String,
    @Json(name = "emailCuidador") val emailCuidador: String,
    @Json(name = "relacaoCuidador") val relacaoCuidador: String,
    @Json(name = "cpfCuidador") val cpfCuidador: String,
)