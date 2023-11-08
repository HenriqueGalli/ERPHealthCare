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

    @GET("/exams/")
    suspend fun getExames(): Response<Any>

    @POST("/exams/")
    suspend fun postExame(
        @Body exame: Exame
    ): Response<Any>

    @DELETE("/exams/{id}/")
    suspend fun deleteExame(
        @Path("id") idExame: Int
    ): Response<Any>

    @PUT("/exams/{id}/")
    suspend fun updateExame(
        @Path("id") idExame: Int,
        @Body exame: Exame
    ): Response<Any>

    @GET("/user/medical-history/caregiver/{idUsuario}/")
    suspend fun getMedicalHistoryCuidador(
        @Path("idUsuario") idPaciente: Int
    ): HistoricoMedico

    @GET("/heart-rate/history/")
    suspend fun getBatimentos(@Query("dateTimeMedicao") dataMedicao: String): ArrayList<MedicoesSinaisVitais>

    @GET("heart-rate/history/caregiver/{idUsuario}/")
    suspend fun getBatimentosCuidador(
        @Path("idUsuario") idUsuario: Int, @Query("dateTimeMedicao") date: String
    ): ArrayList<MedicoesSinaisVitais>

    @GET("/oxygen-level/history/")
    suspend fun getOxigenacao(
        @Query("dateTimeMedicao") dataMedicao: String
    ): ArrayList<MedicoesSinaisVitais>

    @GET("/oxygen-level/history/caregiver/{idUsuario}/")
    suspend fun getOxigenacaoCuidador(
        @Path("idUsuario") idUsuario: Int,
        @Query("dateTimeMedicao") dataMedicao: String
    ): ArrayList<MedicoesSinaisVitais>

    @GET("/corporal-temperature/history/")
    suspend fun getTemperatura(@Query("dateTimeMedicao") dataMedicao: String): ArrayList<MedicoesSinaisVitais>

    @GET("/corporal-temperature/history/caregiver/{idUsuario}/")
    suspend fun getTemperaturaCuidador(
        @Path("idUsuario") idUsuario: Int,
        @Query("dateTimeMedicao") dataMedicao: String
    ): ArrayList<MedicoesSinaisVitais>

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

    @PUT("/user/medical-history/caregiver/{idUsuario}/")
    suspend fun updateHistoricoMedicoCuidador(
        @Path("idUsuario") idUsuario: Int, @Body historico: HistoricoMedico
    ): HistoricoMedico

    @GET("caregiver/")
    suspend fun getCuidador(): Cuidador

    @GET("caregiver/association/")
    suspend fun getListaPacientes(): ArrayList<Paciente>

    @GET("/exams/caregiver/{idUsuario}/")
    suspend fun getExamesCuidador(@Path("idUsuario") idUsuario: Int): Response<Any>

    @POST("/exams/caregiver/{idUsuario}/")
    suspend fun postExameCuidador(
        @Body exame: Exame,
        @Path("idUsuario") idUsuario: Int
    ): Response<Any>

    @PUT("/exams/caregiver/{idUsuario}/exam/{idExame}/")
    suspend fun updateExameCuidador(
        @Path("idUsuario") idUsuario: Int,
        @Path("idExame") idExame: Int,
        @Body exame: Exame
    ): Response<Any>

    @DELETE("/exams/caregiver/{idUsuario}/exam/{idExame}/")
    suspend fun deleteExameCuidador(
        @Path("idUsuario") idUsuario: Int,
        @Path("idExame") idExame: Int,
    ): Response<Any>
}