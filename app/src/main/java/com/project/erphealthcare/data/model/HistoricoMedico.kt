package com.project.erphealthcare.data.model

import com.squareup.moshi.Json
import java.io.Serializable

data class HistoricoMedico(
    @Json(name = "historicoFamiliar") var historicoFamiliar: ArrayList<String>?,
    @Json(name = "historicoMedicoProgresso") var historicoMedicoProgresso: ArrayList<String>?,
    @Json(name = "alergias") var alergias: ArrayList<String>?,
    @Json(name = "cirurgias") var cirurgias: ArrayList<String>?,
    @Json(name = "doencas") var doencas: ArrayList<String>?,
    @Json(name = "medicamentosAtuais") var medicamentosAtuais: ArrayList<String>?,
    @Json(name = "medicamentosAnteriores") var medicamentosAnteriores: ArrayList<String>?,
    @Json(name = "vacinas") var vacinas: ArrayList<String>?
): Serializable
