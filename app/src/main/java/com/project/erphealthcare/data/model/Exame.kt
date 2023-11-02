package com.project.erphealthcare.data.model

import com.squareup.moshi.Json
import java.io.Serializable

data class Exame(
    @Json(name = "id") var id: Double,
    @Json(name = "nomeExame") var nomeExame: String,
    @Json(name = "arquivoExame") var arquivoExame: String,
    @Json(name = "dataExame") var dataExame: String,
    @Json(name = "nomeMedico") var nomeMedico: String,
    @Json(name = "idUsuario") var idUsuario: String

) : Serializable {
    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + nomeExame.hashCode()
        result = 31 * result + arquivoExame.hashCode()
        result = 31 * result + dataExame.hashCode()
        result = 31 * result + nomeMedico.hashCode()
        result = 31 * result + idUsuario.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Exame

        if (id != other.id) return false
        if (nomeExame != other.nomeExame) return false
        if (arquivoExame != other.arquivoExame) return false
        if (dataExame != other.dataExame) return false
        if (nomeMedico != other.nomeMedico) return false
        if (idUsuario != other.idUsuario) return false

        return true
    }
}
