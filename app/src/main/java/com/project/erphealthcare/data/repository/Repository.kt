package com.project.erphealthcare.data.repository

import com.project.erphealthcare.data.api.ERPDataSource
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.data.result.CreatePacienteResult
import com.project.erphealthcare.data.result.LoginResult

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class Repository(val dataSource: ERPDataSource) {

    suspend fun login(username: String, password: String): LoginResult {
        return dataSource.login(username, password)
    }

    suspend fun createPaciente(user: Paciente): CreatePacienteResult {
        return dataSource.createPaciente(user)
    }
}