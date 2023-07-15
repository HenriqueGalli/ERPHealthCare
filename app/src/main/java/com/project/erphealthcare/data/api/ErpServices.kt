package com.project.erphealthcare.data.api

import com.project.erphealthcare.data.model.LoginResponse
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.LoginResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

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
}