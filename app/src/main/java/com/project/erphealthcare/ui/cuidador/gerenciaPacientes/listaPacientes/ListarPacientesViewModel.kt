package com.project.erphealthcare.ui.cuidador.gerenciaPacientes.listaPacientes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.GetListaPacienteResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ListarPacientesViewModel(private val repository: Repository) : ViewModel() {

    val pacienteLiveData: MutableLiveData<GetListaPacienteResult> = MutableLiveData()
    fun getListaPacientes(token: String) = runBlocking {
        launch {
            val res = repository.getListaPaciente(token)
            pacienteLiveData.postValue(res)
        }
    }

}