package com.project.erphealthcare.ui.paciente.exames

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.erphealthcare.data.model.HistoricoMedico
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.GetExamesResult
import com.project.erphealthcare.data.result.GetMedicalHistoryResult
import com.project.erphealthcare.data.result.UpdateMedicalHistoryResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ListaExamesViewModel(private val repository: Repository): ViewModel() {

    val examesResult: MutableLiveData<GetExamesResult> = MutableLiveData()

    fun getMedicalHistory() = runBlocking {
        launch {
            val res = repository.getExames()
            examesResult.postValue(res)
        }
    }
}