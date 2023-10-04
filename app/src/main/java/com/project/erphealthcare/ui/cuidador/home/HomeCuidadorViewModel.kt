package com.project.erphealthcare.ui.cuidador.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.GetCuidadorResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeCuidadorViewModel(private val repository: Repository) : ViewModel() {

    var cuidadorLiveData: MutableLiveData<GetCuidadorResult?> = MutableLiveData()
    //val historicoMedicoLiveData: MutableLiveData<GetMedicalHistoryResult> = MutableLiveData()

    fun getCuidador(token: String) = runBlocking {
        launch {
            val res = repository.getCuidador(token)
            cuidadorLiveData.postValue(res)
        }
    }
}