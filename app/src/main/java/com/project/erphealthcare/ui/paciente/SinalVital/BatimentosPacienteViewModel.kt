package com.project.erphealthcare.ui.paciente.SinalVital

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.GetSinaisVitaisResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BatimentosPacienteViewModel(private val repository: Repository): ViewModel() {

    val medicaoResult: MutableLiveData<GetSinaisVitaisResult> = MutableLiveData()

    fun getBatimentosCardiacos() = runBlocking {
        launch {
            val res = repository.getBatimentosCardiacos()
            medicaoResult.postValue(res)
        }
    }

    fun getOxigenacaoSanguinea() = runBlocking {
        launch {
            val res = repository.getOxigenacaoSanguinea()
            medicaoResult.postValue(res)
        }
    }
    fun getTemperaturaCorporal() = runBlocking {
        launch {
            val res = repository.getTemperaturaCorporal()
            medicaoResult.postValue(res)
        }
    }

}