package com.project.erphealthcare.data.model

import com.squareup.moshi.Json
import java.io.Serializable

data class Cuidador(
    @Json(name = "nomeCuidador") var nomeCuidador: String?,
    @Json(name = "emailCuidador") var emailCuidador: String?,
    @Json(name = "relacaoCuidador") var relacaoCuidador: String?,
    @Json(name = "cpfCuidador") var cpfCuidador: String?,
    @Json(name = "senhaCuidador") var senhaCuidador: String?
) : Serializable