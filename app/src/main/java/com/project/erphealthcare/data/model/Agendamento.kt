package com.project.erphealthcare.data.model

import com.squareup.moshi.Json

data class Agendamento(
    @Json(name = "id") var id: Int?,
    @Json(name = "idUsuario") var idUsuario: Int?,
    @Json(name = "dataAgendamento") var dataAgendamento: String?,
    @Json(name = "tipoAgendamento") var tipoAgendamento: String?,
    @Json(name = "descricaoAgendamento") var descricaoAgendamento: String?,
    @Json(name = "nomeMedico") var nomeMedico: String?,
    @Json(name = "localizacao") var localizacao: String?,
    @Json(name = "telefoneMedico") var telefoneMedico: String?,
    @Json(name = "observacoes") var observacoes: String?,
)
