package com.project.erphealthcare.data.repository

import br.com.preventivewelfare.api.result.DeleteUserResult
import br.com.preventivewelfare.api.result.EditUserResult
import com.project.erphealthcare.data.api.ApiService
import com.project.erphealthcare.data.api.ERPDataSource
import com.project.erphealthcare.data.model.Agendamento
import com.project.erphealthcare.data.model.Cuidador
import com.project.erphealthcare.data.model.Exame
import com.project.erphealthcare.data.model.HistoricoMedico
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.AssociateCaregiverUserResult
import com.project.erphealthcare.data.result.CreateAgendamentoResult
import com.project.erphealthcare.data.result.CreateCuidadorResult
import com.project.erphealthcare.data.result.CreateExamesResult
import com.project.erphealthcare.data.result.CreatePacienteResult
import com.project.erphealthcare.data.result.DeleteExamesResult
import com.project.erphealthcare.data.result.GetCalendarioResult
import com.project.erphealthcare.data.result.GetCuidadorResult
import com.project.erphealthcare.data.result.GetExamesResult
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

    suspend fun associateCaregiver(email: String, cpf: String): AssociateCaregiverUserResult? {
        return dataSource.associateCaregiver(userEmail = email, cpfUser = cpf)
    }

    suspend fun deleteAssociationCaregiver(
        email: String,
        cpf: String
    ): AssociateCaregiverUserResult? {
        return dataSource.deleteAssociationCaregiver(userEmail = email, cpfUser = cpf)
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

    suspend fun updateMedicalHistoryCuidador(
        idPaciente: Int,
        historico: HistoricoMedico
    ): UpdateMedicalHistoryResult {
        return dataSource.updateMedicalHistoryCuidador(historico, idPaciente)
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

    suspend fun getCalendario(): GetCalendarioResult {
        return dataSource.getCalendario()
    }

    suspend fun getExames(): GetExamesResult {
        return dataSource.getExames()
    }

    suspend fun postExames(exame: Exame): CreateExamesResult {
        return dataSource.postExames(exame = exame)
    }
    suspend fun createAgendamento(agendamento: Agendamento): CreateAgendamentoResult {
        return dataSource.createAgendamento(agendamento)
    }

    suspend fun deleteExame(id: Int): DeleteExamesResult {
        return dataSource.deleteExame(id)
    }

    suspend fun getBatimentosCardiacos(dataMedicao: String): GetSinaisVitaisResult {
        return dataSource.getBatimentosCardiacos(dataMedicao)
    }

    suspend fun getBatimentosCardiacosCuidador(
        idPaciente: Int,
        dataMedicao: String
    ): GetSinaisVitaisResult {
        return dataSource.getBatimentosCardiacosCuidador(idPaciente, dataMedicao)
    }

    suspend fun getOxigenacaoSanguinea(dataMedicao: String): GetSinaisVitaisResult {
        return dataSource.getOxigenacaoSanguinea(dataMedicao)
    }

    suspend fun getOxigenacaoSanguineaCuidador(idPaciente: Int,dataMedicao: String): GetSinaisVitaisResult {
        return dataSource.getOxigenacaoSanguineaCuidador(idPaciente, dataMedicao)
    }

    suspend fun getTemperaturaCorporal(dataMedicao: String): GetSinaisVitaisResult {
        return dataSource.getTemperaturaCorporal(dataMedicao)
    }

    suspend fun getTemperaturaCorporalCuidador(idPaciente: Int, dataMedicao: String): GetSinaisVitaisResult {
        return dataSource.getTemperaturaCorporalCuidador(idPaciente, dataMedicao)
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

    suspend fun getMedicalHistoryCuidador(idPaciente: Int): GetMedicalHistoryResult? {
        return dataSource.getMedicalHistoryCuidador(idPaciente)
    }

    suspend fun updateExame(exame: Exame): CreateExamesResult {
        return dataSource.updateExame(exame)
    }

    suspend fun getExamesCuidador(intExtra: Int): GetExamesResult? {
        return dataSource.getExamesCuidador(intExtra)
    }

    suspend fun updateExameCuidador(exame: Exame, idPaciente: Int): CreateExamesResult {
        return dataSource.updateExameCuidador(idPaciente, exame)
    }

    suspend fun postExamesCuidador(exame: Exame, idPaciente: Int): CreateExamesResult {
        return dataSource.postExamesCuidador(exame = exame, idPaciente = idPaciente)
    }

    suspend fun deleteExameCuidador(id: Int, idPaciente: Int): DeleteExamesResult {
        return dataSource.deleteExameCuidador(id = id, idPaciente = idPaciente)
    }
}