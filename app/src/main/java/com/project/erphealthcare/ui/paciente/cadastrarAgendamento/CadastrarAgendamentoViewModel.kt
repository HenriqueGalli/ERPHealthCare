package com.project.erphealthcare.ui.paciente.cadastrarAgendamento

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.preventivewelfare.api.result.DeleteUserResult
import br.com.preventivewelfare.api.result.EditUserResult
import com.project.erphealthcare.data.model.Agendamento
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.CreateAgendamentoResult
import com.project.erphealthcare.data.result.CreatePacienteResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CadastrarAgendamentoViewModel(private val repository: Repository) : ViewModel() {

    val cadastrarLiveData: MutableLiveData<CreateAgendamentoResult> = MutableLiveData()

    fun cadastrarAgendamento(agenda: Agendamento) = runBlocking {
        launch {
            val res = repository.createAgendamento(agenda)
            cadastrarLiveData.value = res
        }
    }

}