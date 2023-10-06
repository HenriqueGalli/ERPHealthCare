package com.project.erphealthcare.data.repository

import br.com.preventivewelfare.api.result.DeleteUserResult
import br.com.preventivewelfare.api.result.EditUserResult
import com.project.erphealthcare.data.api.ApiService
import com.project.erphealthcare.data.api.ERPDataSource
import com.project.erphealthcare.data.model.Cuidador
import com.project.erphealthcare.data.model.HistoricoMedico
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.CreateCuidadorResult
import com.project.erphealthcare.data.result.CreatePacienteResult
import com.project.erphealthcare.data.result.GetCuidadorResult
import com.project.erphealthcare.data.result.GetListaPacienteResult
import com.project.erphealthcare.data.result.GetMedicalHistoryResult
import com.project.erphealthcare.data.result.GetPacienteResult
import com.project.erphealthcare.data.result.GetSinaisVitaisResult
import com.project.erphealthcare.data.result.LoginResult
import com.project.erphealthcare.data.result.UpdateMedicalHistoryResult

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class Repository(val dataSource: ERPDataSource) {

    suspend fun login(username: String, password: String): LoginResult {
        return dataSource.login(username, password)
    }

    suspend fun createPaciente(user: Paciente): CreatePacienteResult {
        return dataSource.createPaciente(user)
    }

    suspend fun createCuidador(cuidador: Cuidador): CreateCuidadorResult {
        return dataSource.createCuidador(cuidador)
    }

    suspend fun createMedicalHistory(historico: HistoricoMedico): GetMedicalHistoryResult {
        return dataSource.createMedicalHistory(historico)
    }

    suspend fun updateMedicalHistory(historico: HistoricoMedico): UpdateMedicalHistoryResult {
        return dataSource.updateMedicalHistory(historico)
    }

    suspend fun editPaciente(user: Paciente): EditUserResult {
        return dataSource.editPaciente(user)
    }

    suspend fun editCuidador(user: Cuidador): EditUserResult {
        return dataSource.editCuidador(user)
    }

    suspend fun getPaciente(token: String): GetPacienteResult {
        return dataSource.getPaciente(token)
    }

    suspend fun getListaPaciente(token: String): GetListaPacienteResult {
        return dataSource.getListaPaciente(token)
    }

    suspend fun getMedicalHistory(): GetMedicalHistoryResult {
        return dataSource.getMedicalHistory()
    }

    suspend fun getBatimentosCardiacos(): GetSinaisVitaisResult {
        return dataSource.getBatimentosCardiacos()
    }

    suspend fun getOxigenacaoSanguinea(): GetSinaisVitaisResult {
        return dataSource.getOxigenacaoSanguinea()
    }
    suspend fun getTemperaturaCorporal(): GetSinaisVitaisResult {
        return dataSource.getTemperaturaCorporal()
    }

    suspend fun deleteUser(): DeleteUserResult? {
        return try {
            val res = ApiService.service.deleteUser()
            if (res.code() == 204)
                DeleteUserResult.Success
            else
                DeleteUserResult.ServerError
        } catch (throwable: Throwable) {
            DeleteUserResult.ServerError
        }
    }

    suspend fun deleteCuidador(): DeleteUserResult? {
        return try {
            val res = ApiService.service.deleteCuidador()
            if (res.code() == 204)
                DeleteUserResult.Success
            else
                DeleteUserResult.ServerError
        } catch (throwable: Throwable) {
            DeleteUserResult.ServerError
        }
    }

    suspend fun getCuidador(token: String): GetCuidadorResult? {
        return dataSource.getCuidador(token)
    }
}