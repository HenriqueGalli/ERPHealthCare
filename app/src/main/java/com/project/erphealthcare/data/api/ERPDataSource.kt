package com.project.erphealthcare.data.api

import android.util.Log
import br.com.preventivewelfare.api.result.EditUserResult
import com.google.gson.internal.LinkedTreeMap
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

    suspend fun associateCaregiver(
        userEmail: String, cpfUser: String
    ): AssociateCaregiverUserResult {
        return try {
            ApiService.service.associateCaregiver(userEmail, cpfUser)
            AssociateCaregiverUserResult.Success
        } catch (throwable: Throwable) {
            if (throwable.message?.contains("End of input at line 1 column 1 path \$") == true)
                AssociateCaregiverUserResult.Success
            else
                AssociateCaregiverUserResult.ServerError
        }
    }

    suspend fun deleteAssociationCaregiver(
        userEmail: String, cpfUser: String
    ): AssociateCaregiverUserResult {
        return try {
            ApiService.service.deleteAssociationCaregiver(userEmail, cpfUser)
            AssociateCaregiverUserResult.Success
        } catch (throwable: Throwable) {
            AssociateCaregiverUserResult.ServerError
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

    suspend fun createAgendamento(agendamento: Agendamento): CreateAgendamentoResult {
        return try {
            ApiService.service.postCalendario(agendamento)
            CreateAgendamentoResult.Success
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                CreateAgendamentoResult.ApiError(401)
            } else CreateAgendamentoResult.ServerError
        }
    }

    suspend fun createCuidador(cuidador: Cuidador): CreateCuidadorResult {
        return try {
            val res = ApiService.service.createCuidador(cuidador)
            CreateCuidadorResult.Success
        } catch (throwable: Throwable) {
            if (throwable is HttpException) {
                CreateCuidadorResult.ApiError(401)
            } else CreateCuidadorResult.ServerError
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

    suspend fun editCuidador(user: Cuidador): EditUserResult {
        return try {
            val res = ApiService.service.updateCuidador(user)
            EditUserResult.Success
        } catch (throwable: Throwable) {
            EditUserResult.ServerError
        }
    }

    suspend fun getPaciente(token: String): GetPacienteResult {
        return try {
            if (token.isNotEmpty()) {
                ApiService.token = token
            }
            val res = ApiService.service.getPaciente()
            GetPacienteResult.Success(res)
        } catch (throwable: Throwable) {
            val message = throwable
            Log.d("", throwable.message.toString())
            GetPacienteResult.ServerError
        }
    }

    suspend fun getMedicalHistory(): GetMedicalHistoryResult {
        return try {
            val res = ApiService.service.getMedicalHistory()
            GetMedicalHistoryResult.Success(res)
        } catch (throwable: Throwable) {
            GetMedicalHistoryResult.ServerError
        }
    }

    suspend fun getCalendario(): GetCalendarioResult {
        return try {
            val res = ApiService.service.getCalendario()
            GetCalendarioResult.Success(res)
        } catch (throwable: Throwable) {
            GetCalendarioResult.ServerError
        }
    }

    suspend fun getExames(): GetExamesResult {
        return try {
            val res = ApiService.service.getExames()
            GetExamesResult.Success(res.body() as ArrayList<LinkedTreeMap<Any, Any>>)
        } catch (throwable: Throwable) {
            GetExamesResult.ServerError
        }
    }

    suspend fun postExames(exame: Exame): CreateExamesResult {
        return try {
            val res = ApiService.service.postExame(exame)
            CreateExamesResult.Success(res.body() as LinkedTreeMap<Any, Any>)
        } catch (throwable: Throwable) {
            CreateExamesResult.ServerError
        }
    }

    suspend fun deleteExame(id: Int): DeleteExamesResult {
        return try {
            ApiService.service.deleteExame(id)
            DeleteExamesResult.Success
        } catch (throwable: Throwable) {
            DeleteExamesResult.ServerError
        }
    }

    suspend fun getMedicalHistoryCuidador(idPaciente: Int): GetMedicalHistoryResult {
        return try {
            val res = ApiService.service.getMedicalHistoryCuidador(idPaciente)
            GetMedicalHistoryResult.Success(res)
        } catch (throwable: Throwable) {
            GetMedicalHistoryResult.ServerError
        }
    }

    suspend fun getBatimentosCardiacos(dataMedicao: String): GetSinaisVitaisResult {
        return try {
            val res = ApiService.service.getBatimentos(dataMedicao)
            GetSinaisVitaisResult.Success(res)
        } catch (throwable: Throwable) {
            GetSinaisVitaisResult.ServerError
        }
    }

    suspend fun getBatimentosCardiacosCuidador(
        idUsuario: Int,
        dataMedicao: String
    ): GetSinaisVitaisResult {
        return try {
            val res = ApiService.service.getBatimentosCuidador(idUsuario = idUsuario, dataMedicao)
            GetSinaisVitaisResult.Success(res)
        } catch (throwable: Throwable) {
            GetSinaisVitaisResult.ServerError
        }
    }

    suspend fun getOxigenacaoSanguinea(dataMedicao: String): GetSinaisVitaisResult {
        return try {
            val res = ApiService.service.getOxigenacao(dataMedicao)
            GetSinaisVitaisResult.Success(res)
        } catch (throwable: Throwable) {
            GetSinaisVitaisResult.ServerError
        }
    }

    suspend fun getOxigenacaoSanguineaCuidador(idUsuario: Int, dataMedicao: String): GetSinaisVitaisResult {
        return try {
            val res = ApiService.service.getOxigenacaoCuidador(idUsuario, dataMedicao)
            GetSinaisVitaisResult.Success(res)
        } catch (throwable: Throwable) {
            GetSinaisVitaisResult.ServerError
        }
    }

    suspend fun getTemperaturaCorporal(dataMedicao: String): GetSinaisVitaisResult {
        return try {
            val res = ApiService.service.getTemperatura(dataMedicao)
            GetSinaisVitaisResult.Success(res)
        } catch (throwable: Throwable) {
            GetSinaisVitaisResult.ServerError
        }
    }

    suspend fun getTemperaturaCorporalCuidador(idPaciente: Int, dataMedicao: String): GetSinaisVitaisResult {
        return try {
            val res = ApiService.service.getTemperaturaCuidador(idPaciente, dataMedicao)
            GetSinaisVitaisResult.Success(res)
        } catch (throwable: Throwable) {
            GetSinaisVitaisResult.ServerError
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

    suspend fun updateMedicalHistory(historico: HistoricoMedico): UpdateMedicalHistoryResult {
        return try {
            val res = ApiService.service.updateHistoricoMedico(historico)
            UpdateMedicalHistoryResult.Success(res)
        } catch (throwable: Throwable) {
            UpdateMedicalHistoryResult.ServerError
        }
    }

    suspend fun updateMedicalHistoryCuidador(
        historico: HistoricoMedico,
        idCuidador: Int
    ): UpdateMedicalHistoryResult {
        return try {
            val res = ApiService.service.updateHistoricoMedicoCuidador(idCuidador, historico)
            UpdateMedicalHistoryResult.Success(res)
        } catch (throwable: Throwable) {
            UpdateMedicalHistoryResult.ServerError
        }
    }

    suspend fun getCuidador(token: String): GetCuidadorResult? {
        return try {
            if (token.isNotEmpty()) {
                ApiService.token = token
            }
            val res = ApiService.service.getCuidador()
            GetCuidadorResult.Success(res)
        } catch (throwable: Throwable) {
            val message = throwable
            Log.d("", throwable.message.toString())
            GetCuidadorResult.ServerError
        }
    }

    suspend fun getListaPaciente(token: String): GetListaPacienteResult {
        return try {
            if (token.isNotEmpty()) {
                ApiService.token = token
            }
            val res = ApiService.service.getListaPacientes()
            GetListaPacienteResult.Success(res)
        } catch (throwable: Throwable) {
            GetListaPacienteResult.ServerError
        }
    }

    suspend fun updateExame(exame: Exame): CreateExamesResult {
        return try {
            val res = ApiService.service.updateExame(exame.id.toInt(), exame)
            CreateExamesResult.Success(res.body() as LinkedTreeMap<Any, Any>)
        } catch (throwable: Throwable) {
            CreateExamesResult.ServerError
        }
    }

    suspend fun getExamesCuidador(intExtra: Int): GetExamesResult? {
        return try {
            val res = ApiService.service.getExamesCuidador(intExtra)
            GetExamesResult.Success(res.body() as ArrayList<LinkedTreeMap<Any, Any>>)
        } catch (throwable: Throwable) {
            GetExamesResult.ServerError
        }
    }

    suspend fun updateExameCuidador(idPaciente: Int, exame: Exame): CreateExamesResult {
        return try {
            val res = ApiService.service.updateExameCuidador(
                idExame = exame.id.toInt(),
                exame = exame,
                idUsuario = idPaciente
            )
            CreateExamesResult.Success(res.body() as LinkedTreeMap<Any, Any>)
        } catch (throwable: Throwable) {
            CreateExamesResult.ServerError
        }
    }


    suspend fun postExamesCuidador(idPaciente: Int, exame: Exame): CreateExamesResult {
        return try {
            val res = ApiService.service.postExameCuidador(exame, idPaciente)
            CreateExamesResult.Success(res.body() as LinkedTreeMap<Any, Any>)
        } catch (throwable: Throwable) {
            CreateExamesResult.ServerError
        }
    }

    suspend fun deleteExameCuidador(idPaciente: Int, id: Int): DeleteExamesResult {
        return try {
            ApiService.service.deleteExameCuidador(idPaciente, id)
            DeleteExamesResult.Success
        } catch (throwable: Throwable) {
            DeleteExamesResult.ServerError
        }
    }

}