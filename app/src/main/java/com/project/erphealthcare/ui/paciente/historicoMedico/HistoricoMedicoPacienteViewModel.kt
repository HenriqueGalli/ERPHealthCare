package com.project.erphealthcare.ui.paciente.historicoMedico

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.erphealthcare.data.model.HistoricoMedico
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.GetMedicalHistoryResult
import com.project.erphealthcare.data.result.UpdateMedicalHistoryResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HistoricoMedicoPacienteViewModel(private val repository: Repository): ViewModel() {

    val medicalHistoryResult: MutableLiveData<GetMedicalHistoryResult?> = MutableLiveData()
    val updateAlergiasResult: MutableLiveData<UpdateMedicalHistoryResult> = MutableLiveData()

    fun getMedicalHistory() = runBlocking {
        launch {
            val res = repository.getMedicalHistory()
            medicalHistoryResult.postValue(res)
        }
    }

    fun getMedicalHistoryCuidador(idPaciente: Int) = runBlocking {
        launch {
            val res = repository.getMedicalHistoryCuidador(idPaciente)
            medicalHistoryResult.postValue(res)
        }
    }

    fun updateMedicalHistory(historicoMedico: HistoricoMedico) = runBlocking {
        launch {
            val res = repository.updateMedicalHistory(historicoMedico)
            updateAlergiasResult.postValue(res)
        }
    }

    fun updateMedicalHistoryCuidador(idPaciente: Int, historicoMedico: HistoricoMedico) =
        runBlocking {
            launch {
                val res = repository.updateMedicalHistoryCuidador(idPaciente, historicoMedico)
                updateAlergiasResult.postValue(res)
            }
        }
}