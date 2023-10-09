package com.project.erphealthcare.ui.cuidador.gerenciaPacientes.associarPacientes.associarPaciente

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.AssociateCaregiverUserResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AssociarPacienteViewModel(private val repository: Repository) : ViewModel() {

    val associarLiveData: MutableLiveData<AssociateCaregiverUserResult?> = MutableLiveData()

    fun associarPaciente(email: String, cpf: String) = runBlocking {
        launch {
            val res = repository.associateCaregiver(email, cpf)
            associarLiveData.value = res
        }
    }
}