package com.project.erphealthcare.ui.paciente.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.GetPacienteResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomePacienteViewModel(private val repository: Repository): ViewModel() {

    val pacienteLiveData: MutableLiveData<GetPacienteResult> = MutableLiveData()

    fun getPaciente(token: String) = runBlocking {
        launch {
            val res = repository.getPaciente(token)
            pacienteLiveData.postValue(res)
        }
    }
}