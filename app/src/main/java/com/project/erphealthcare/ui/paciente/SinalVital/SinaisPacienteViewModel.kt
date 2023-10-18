package com.project.erphealthcare.ui.paciente.SinalVital

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.GetSinaisVitaisResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SinaisPacienteViewModel(private val repository: Repository): ViewModel() {

    val medicaoResult: MutableLiveData<GetSinaisVitaisResult> = MutableLiveData()

    fun getBatimentosCardiacos(dataMedicao: String) = runBlocking {
        launch {
            val res = repository.getBatimentosCardiacos(dataMedicao)
            medicaoResult.postValue(res)
        }
    }

    fun getOxigenacaoSanguinea(dataMedicao: String) = runBlocking {
        launch {
            val res = repository.getOxigenacaoSanguinea(dataMedicao)
            medicaoResult.postValue(res)
        }
    }
    fun getTemperaturaCorporal(dataMedicao: String) = runBlocking {
        launch {
            val res = repository.getTemperaturaCorporal(dataMedicao)
            medicaoResult.postValue(res)
        }
    }

}