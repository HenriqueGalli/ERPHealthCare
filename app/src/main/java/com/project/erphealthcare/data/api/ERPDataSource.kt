package com.project.erphealthcare.data.api

import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.CreatePacienteResult
import com.project.erphealthcare.data.result.LoginResult
import retrofit2.HttpException

class ERPDataSource {

    suspend fun login(username: String, password: String): LoginResult {
        return try {
            val loginResponse = ApiService.service.validateLogin(username, password)
            LoginResult.Success(loginResponse)
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                LoginResult.ApiError(401)
            } else LoginResult.ServerError
        }
    }

    suspend fun createPaciente(paciente: Paciente): CreatePacienteResult {
        return try {
            val res = ApiService.service.createPaciente(paciente)
            CreatePacienteResult.Success
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                CreatePacienteResult.ApiError(401)
            } else CreatePacienteResult.ServerError
        }
    }
}