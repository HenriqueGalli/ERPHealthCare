package com.project.erphealthcare.ui.cuidador.gerenciaPacientes.listaPacientes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.AssociateCaregiverUserResult
import com.project.erphealthcare.data.result.GetListaPacienteResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ListarPacientesViewModel(private val repository: Repository) : ViewModel() {

    val pacienteLiveData: MutableLiveData<GetListaPacienteResult> = MutableLiveData()
    val associarLiveData: MutableLiveData<AssociateCaregiverUserResult?> = MutableLiveData()
    fun getListaPacientes(token: String) = runBlocking {
        launch {
            val res = repository.getListaPaciente(token)
            pacienteLiveData.postValue(res)
        }
    }

    fun deletarAssociacaoPaciente(email: String, cpf: String) = runBlocking {
        launch {
            val res = repository.deleteAssociationCaregiver(email, cpf)
            associarLiveData.value = res
        }
    }

}