package com.project.erphealthcare.ui.cuidador.gerenciaPacientes.associarPacientes.associarPaciente

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.erphealthcare.data.api.ERPDataSource
import com.project.erphealthcare.data.repository.Repository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class AssociarPacienteViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AssociarPacienteViewModel::class.java)) {
            return AssociarPacienteViewModel(
                repository = Repository(
                    dataSource = ERPDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}