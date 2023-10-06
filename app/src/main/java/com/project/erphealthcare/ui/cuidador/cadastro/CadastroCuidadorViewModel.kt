package com.project.erphealthcare.ui.cuidador.cadastro

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.preventivewelfare.api.result.DeleteUserResult
import br.com.preventivewelfare.api.result.EditUserResult
import com.project.erphealthcare.data.model.Cuidador
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.CreateCuidadorResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CadastroCuidadorViewModel(private val repository: Repository) : ViewModel() {

    val cadastrarLiveData: MutableLiveData<CreateCuidadorResult> = MutableLiveData()
    val excluirLiveData: MutableLiveData<DeleteUserResult> = MutableLiveData()
    val editarLiveData: MutableLiveData<EditUserResult> = MutableLiveData()

    fun cadastrarPaciente(user: Cuidador) = runBlocking {
        launch {
            val res = repository.createCuidador(user)
            cadastrarLiveData.value = res
        }
    }

    fun editar(user: Cuidador) = runBlocking {
        launch {
            val res = repository.editCuidador(user)
            editarLiveData.postValue(res)
        }
    }

    fun deletar() = runBlocking {
        launch {
            val res = repository.deleteCuidador()
            excluirLiveData.value = res
        }
    }
}