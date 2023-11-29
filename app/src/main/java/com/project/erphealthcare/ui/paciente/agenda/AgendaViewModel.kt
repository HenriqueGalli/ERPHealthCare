package com.project.erphealthcare.ui.paciente.agenda

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.GetCalendarioResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AgendaViewModel(private val repository: Repository) : ViewModel() {

    val getCalendarioResult: MutableLiveData<GetCalendarioResult?> = MutableLiveData()

    fun getCalendario() = runBlocking {
        launch {
            val res = repository.getCalendario()
            getCalendarioResult.postValue(res)
        }
    }
}