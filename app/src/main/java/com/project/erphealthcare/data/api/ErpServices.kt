package com.project.erphealthcare.data.api

import com.project.erphealthcare.data.model.*
import com.project.erphealthcare.data.result.GetMedicalHistoryResult
import com.project.erphealthcare.data.result.LoginResult
import retrofit2.Response
import retrofit2.http.*

interface ErpServices {

    @GET("user/authentication")
    suspend fun validateLogin(
        @Query("email") user: String,
        @Query("senha") password: String
    ): LoginResponse

    @POST("user/")
    suspend fun createPaciente(
        @Body user: Paciente
    ): Paciente

    @PUT("user/")
    suspend fun updateUser(
        @Body user: Paciente
    ): Paciente

    @GET("user/")
    suspend fun getPaciente(): Paciente

    @GET("/user/medical-history/")
    suspend fun getMedicalHistory(): HistoricoMedico

    @GET("/heart-rate/history/")
    suspend fun getBatimentos(): ArrayList<MedicoesSinaisVitais>

    @DELETE("user/")
    suspend fun deleteUser(): Response<Any>

    @POST("user/medical-history/")
    suspend fun createHistoricoMedico(
        @Body historico: HistoricoMedico): HistoricoMedico

    @PUT("user/medical-history/")
    suspend fun updateHistoricoMedico(
        @Body historico: HistoricoMedico): HistoricoMedico
}