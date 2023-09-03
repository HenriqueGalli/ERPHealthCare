package com.project.erphealthcare.ui.paciente.batimentos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.erphealthcare.data.model.HistoricoMedico
import com.project.erphealthcare.data.repository.Repository
import com.project.erphealthcare.data.result.GetMedicalHistoryResult
import com.project.erphealthcare.data.result.GetSinaisVitaisResult
import com.project.erphealthcare.data.result.UpdateMedicalHistoryResult
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BatimentosPacienteViewModel(private val repository: Repository): ViewModel() {

    val batimentosResult: MutableLiveData<GetSinaisVitaisResult> = MutableLiveData()

    fun getBatimentosCardiacos() = runBlocking {
        launch {
            val res = repository.getBatimentosCardiacos()
            batimentosResult.postValue(res)
        }
    }
}