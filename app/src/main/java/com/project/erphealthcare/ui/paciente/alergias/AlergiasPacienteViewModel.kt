package com.project.erphealthcare.ui.paciente.alergias

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.GetMedicalHistoryResult
import com.project.erphealthcare.data.result.GetPacienteResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AlergiasPacienteViewModel(private val repository: Repository): ViewModel() {

    val result: MutableLiveData<GetMedicalHistoryResult> = MutableLiveData()

    fun getMedicalHistory() = runBlocking {
        launch {
            val res = repository.getMedicalHistory()
            result.postValue(res)
        }
    }
}