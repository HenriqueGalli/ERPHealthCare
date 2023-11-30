package com.project.erphealthcare.ui.paciente.cadastrarAgendamento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.project.erphealthcare.data.api.ERPDataSource
import com.project.erphealthcare.data.repository.Repository

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class CadastrarAgendamentoViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CadastrarAgendamentoViewModel::class.java)) {
            return CadastrarAgendamentoViewModel(
                repository = Repository(
                    dataSource = ERPDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}