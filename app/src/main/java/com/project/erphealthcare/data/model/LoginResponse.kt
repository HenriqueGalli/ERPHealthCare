package com.project.erphealthcare.data.model

import com.squareup.moshi.Json

data class LoginResponse(
    @Json(name = "cuidador") val cuidador: Boolean,
    @Json(name = "authentication") val authentication: Boolean,
    @Json(name = "apiKey") val apiKey: String
)
