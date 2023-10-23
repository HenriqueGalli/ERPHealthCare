package com.project.erphealthcare.data.model

import com.squareup.moshi.Json
import java.io.Serializable

data class Exame(
    @Json(name = "id") var id: String,
    @Json(name = "nomeExame") var nomeExame: String,
    @Json(name = "arquivoExame") var arquivoExame: ByteArray,
    @Json(name = "dataExame") var dataExame: String,
    @Json(name = "nomeMedico") var nomeMedico: String,
    @Json(name = "idUsuario") var idUsuario: String

) :Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Exame

        if (nomeExame != other.nomeExame) return false
        if (!arquivoExame.contentEquals(other.arquivoExame)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = nomeExame.hashCode()
        result = 31 * result + arquivoExame.contentHashCode()
        return result
    }

    fun createExameFromHashMap(hashMap: Map<String, Any>): Exame {
        val id = hashMap["id"].toString()
        val nomeExame = hashMap["nomeExame"].toString()
        val arquivoExame = (hashMap["arquivoExame"] as ByteArray?) ?: ByteArray(0)
        val dataExame = hashMap["dataExame"].toString()
        val nomeMedico = hashMap["nomeMedico"].toString()
        val idUsuario = hashMap["idUsuario"].toString()

        return Exame(id, nomeExame, arquivoExame, dataExame, nomeMedico, idUsuario)
    }
}
