package com.project.erphealthcare.data.api

import android.util.Log
import br.com.preventivewelfare.api.result.EditUserResult
import com.project.erphealthcare.data.model.HistoricoMedico
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.CreatePacienteResult
import com.project.erphealthcare.data.result.GetMedicalHistoryResult
import com.project.erphealthcare.data.result.GetPacienteResult
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
            ApiService.service.createPaciente(paciente)
            CreatePacienteResult.Success
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                CreatePacienteResult.ApiError(401)
            } else CreatePacienteResult.ServerError
        }
    }

    suspend fun editPaciente(user: Paciente): EditUserResult {
        return try {
            val res = ApiService.service.updateUser(user)
            EditUserResult.Success
        } catch (throwable: Throwable) {
            EditUserResult.ServerError
        }
    }

    suspend fun getPaciente(token: String): GetPacienteResult {
        return try {
            if(token.isNotEmpty()){
                ApiService.token = token
            }
            val res = ApiService.service.getPaciente()
            GetPacienteResult.Success(res)
        } catch (throwable: Throwable){
            val message = throwable
            Log.d("", throwable.message.toString())
            GetPacienteResult.ServerError
        }
    }

    suspend fun getMedicalHistory(): GetMedicalHistoryResult {
        return try {
            val res = ApiService.service.getMedicalHistory()
            GetMedicalHistoryResult.Success(res)
        } catch (throwable: Throwable){
            GetMedicalHistoryResult.ServerError
        }
    }

    suspend fun createMedicalHistory(historico: HistoricoMedico): GetMedicalHistoryResult {
        return try {
            val res = ApiService.service.createHistoricoMedico(historico)
            GetMedicalHistoryResult.Success(res)
        } catch (throwable: Throwable) {
            GetMedicalHistoryResult.ServerError
        }
    }

}