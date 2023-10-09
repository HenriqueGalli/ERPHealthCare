package com.project.erphealthcare.data.api

import com.project.erphealthcare.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ErpServices {

    @GET("user/authentication")
    suspend fun validateLogin(
        @Query("email") user: String,
        @Query("senha") password: String
    ): LoginResponse

    @POST("caregiver/association/")
    suspend fun associateCaregiver(
        @Query("userEmail") userEmail: String,
        @Query("userCpf") userCpf: String
    ): AssociarPacienteResponse?

    @DELETE("caregiver/association/")
    suspend fun deleteAssociationCaregiver(
        @Query("userEmail") userEmail: String,
        @Query("userCpf") userCpf: String
    ): Response<Any>

    @POST("user/")
    suspend fun createPaciente(
        @Body user: Paciente
    ): Paciente

    @POST("caregiver/")
    suspend fun createCuidador(
        @Body user: Cuidador
    ): Cuidador

    @PUT("user/")
    suspend fun updateUser(
        @Body user: Paciente
    ): Paciente

    @PUT("caregiver/")
    suspend fun updateCuidador(
        @Body user: Cuidador
    ): Cuidador

    @GET("user/")
    suspend fun getPaciente(): Paciente

    @GET("/user/medical-history/")
    suspend fun getMedicalHistory(): HistoricoMedico

    @GET("/heart-rate/history/")
    suspend fun getBatimentos(): ArrayList<MedicoesSinaisVitais>

    @GET("/oxygen-level/history/")
    suspend fun getOxigenacao(): ArrayList<MedicoesSinaisVitais>

    @GET("/corporal-temperature/history/")
    suspend fun getTemperatura(): ArrayList<MedicoesSinaisVitais>

    @DELETE("user/")
    suspend fun deleteUser(): Response<Any>

    @DELETE("caregiver/")
    suspend fun deleteCuidador(): Response<Any>

    @POST("user/medical-history/")
    suspend fun createHistoricoMedico(
        @Body historico: HistoricoMedico
    ): HistoricoMedico

    @PUT("user/medical-history/")
    suspend fun updateHistoricoMedico(
        @Body historico: HistoricoMedico
    ): HistoricoMedico

    @GET("caregiver/")
    suspend fun getCuidador(): Cuidador

    @GET("caregiver/association/")
    suspend fun getListaPacientes(): ArrayList<Paciente>
}