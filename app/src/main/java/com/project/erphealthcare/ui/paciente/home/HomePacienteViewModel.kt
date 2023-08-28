package com.project.erphealthcare.ui.paciente.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.erphealthcare.data.model.HistoricoMedico
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.GetMedicalHistoryResult
import com.project.erphealthcare.data.result.GetPacienteResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomePacienteViewModel(private val repository: Repository): ViewModel() {

    val pacienteLiveData: MutableLiveData<GetPacienteResult> = MutableLiveData()
    val historicoMedicoLiveData: MutableLiveData<GetMedicalHistoryResult> = MutableLiveData()

    fun getPaciente(token: String) = runBlocking {
        launch {
            val res = repository.getPaciente(token)
            pacienteLiveData.postValue(res)
        }
    }

    fun validateMedicalHistory() = runBlocking {
        launch {
            var res = repository.getMedicalHistory()
            if(res == GetMedicalHistoryResult.ServerError){
               res = repository.createMedicalHistory(createEmptyHistoricoMedico())
            }
            historicoMedicoLiveData.postValue(res)

        }
    }

    fun createEmptyHistoricoMedico(): HistoricoMedico {
        return HistoricoMedico(
            historicoFamiliar = ArrayList(),
            historicoMedicoProgresso = ArrayList(),
            alergias = ArrayList(),
            cirurgias = arrayListOf(),
            doencas = arrayListOf(),
            medicamentosAtuais = arrayListOf(),
            medicamentosAnteriores = arrayListOf(),
            vacinas = arrayListOf()
        )
    }


}