package com.project.erphealthcare.ui.paciente.historicoMedico

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.erphealthcare.data.api.ERPDataSource
import com.project.erphealthcare.data.repository.Repository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class HistoricoMedicoPacienteViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoricoMedicoPacienteViewModel::class.java)) {
            return HistoricoMedicoPacienteViewModel(
                repository = Repository(
                    dataSource = ERPDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}