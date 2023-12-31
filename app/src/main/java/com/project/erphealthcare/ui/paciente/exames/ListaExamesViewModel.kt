package com.project.erphealthcare.ui.paciente.exames

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.erphealthcare.data.model.Exame
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.CreateExamesResult
import com.project.erphealthcare.data.result.DeleteExamesResult
import com.project.erphealthcare.data.result.GetExamesResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ListaExamesViewModel(private val repository: Repository): ViewModel() {

    val examesResult: MutableLiveData<GetExamesResult?> = MutableLiveData()
    val createExamesResult: MutableLiveData<CreateExamesResult> = MutableLiveData()
    val deleteExamesResult: MutableLiveData<DeleteExamesResult> = MutableLiveData()

    fun getExamHistory() = runBlocking {
        launch {
            val res = repository.getExames()
            examesResult.postValue(res)
        }
    }

    fun createExam(exame: Exame) = runBlocking {
        launch {
            val res = repository.postExames(exame)
            createExamesResult.postValue(res)
        }
    }

    fun exclude(id: Int) = runBlocking {
        launch {
            val res = repository.deleteExame(id)
            deleteExamesResult.postValue(res)
        }
    }

    fun updateExame(exame: Exame) = runBlocking {
        launch {
            val res = repository.updateExame(exame)
            createExamesResult.postValue(res)
        }
    }

    fun getExamHistoryCuidador(intExtra: Int) = runBlocking {
        launch {
            val res = repository.getExamesCuidador(intExtra)
            examesResult.postValue(res)
        }
    }

    fun updateExameCuidador(idPacietne: Int, exame: Exame) = runBlocking {
        launch {
            val res = repository.updateExameCuidador(exame, idPacietne)
            createExamesResult.postValue(res)
        }
    }

    fun createExamCuidador(exame: Exame, idPacietne: Int) = runBlocking {
        launch {
            val res = repository.postExamesCuidador(exame, idPacietne)
            createExamesResult.postValue(res)
        }
    }

    fun excludeExamCuidador(id: Int, idPacietne: Int) = runBlocking {
        launch {
            val res = repository.deleteExameCuidador(id, idPacietne)
            deleteExamesResult.postValue(res)
        }
    }


}