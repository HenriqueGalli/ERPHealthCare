package com.project.erphealthcare.ui.paciente.cadastro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.preventivewelfare.api.result.DeleteUserResult
import br.com.preventivewelfare.api.result.EditUserResult
import com.project.erphealthcare.data.model.HistoricoMedico
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.CreatePacienteResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CadastroPacienteViewModel(private val repository: Repository) : ViewModel() {

    val cadastrarLiveData: MutableLiveData<CreatePacienteResult> = MutableLiveData()
    val excluirLiveData: MutableLiveData<DeleteUserResult> = MutableLiveData()
    val editarLiveData: MutableLiveData<EditUserResult> = MutableLiveData()

    fun cadastrarPaciente(user: Paciente) = runBlocking {
        launch {
            val res = repository.createPaciente(user)
            cadastrarLiveData.value = res
        }
    }

    fun editar(user: Paciente) = runBlocking {
        launch {
            val res = repository.editPaciente(user)
            editarLiveData.postValue(res)
        }
    }

    fun deletar() = runBlocking {
        launch {
            val res = repository.deleteUser()
            excluirLiveData.value = res
        }
    }
}