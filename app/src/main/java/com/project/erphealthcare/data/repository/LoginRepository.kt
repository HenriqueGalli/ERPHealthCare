package com.project.erphealthcare.data.repository

import com.project.erphealthcare.data.api.ERPDataSource
import com.project.erphealthcare.data.result.LoginResult

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: ERPDataSource) {

    suspend fun login(username: String, password: String): LoginResult {
        return dataSource.login(username, password)
    }
}