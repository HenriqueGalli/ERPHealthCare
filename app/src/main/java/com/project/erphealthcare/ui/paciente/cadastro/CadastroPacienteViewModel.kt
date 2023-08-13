package com.project.erphealthcare.ui.paciente.cadastro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.erphealthcare.data.api.ERPDataSource
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.CreatePacienteResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CadastroPacienteViewModel(private val repository: Repository): ViewModel() {

    val cadastrarLiveData: MutableLiveData<CreatePacienteResult> = MutableLiveData()

    fun cadastrarPaciente(user: Paciente) = runBlocking {
        launch {
            val res = repository.createPaciente(user)
            cadastrarLiveData.value = res
        }
    }

    class ViewModelFactory(private val dataSource: ERPDataSource) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CadastroPacienteViewModel::class.java)) {
                return modelClass.getConstructor(ERPDataSource::class.java)
                    .newInstance(dataSource)
            }

            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}