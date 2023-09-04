package com.project.erphealthcare.data.model

import com.squareup.moshi.Json
import java.io.Serializable


data class Paciente(
    @Json(name = "id") var id: Int? = null,
    @Json(name = "nome") var nome: String?,
    @Json(name = "nomeMae") var nomeMae: String?,
    @Json(name = "senha") var senha: String?,
    @Json(name = "email") var email: String?,
    @Json(name = "telefone") var telefone: String?,
    @Json(name = "tipoSanguineo") var tipoSanguineo: String?,
    @Json(name = "sexo") var sexo: String?,
    @Json(name = "dataNascimento") var dataNascimento: String?,
    @Json(name = "peso") var peso: Float? = null,
    @Json(name = "altura") var altura: Float? = null,
    @Json(name = "cpf") var cpf: String?,
    @Json(name = "naturalidade") var naturalidade: String?,
    @Json(name = "enderecoCompleto") var enderecoCompleto: String?
): Serializable {
    fun getBirthDay(): Int {
        return dataNascimento?.split('-')?.get(2)?.substring(0,2)?.toInt() ?: 0
    }
    fun getBirthMonth(): Int {
        return dataNascimento?.split('-')?.get(1)?.toInt()?.minus(1) ?: 0
    }
    fun getBirthYear(): Int {
        return dataNascimento?.split('-')?.get(0)?.toInt() ?: 0
    }
}
