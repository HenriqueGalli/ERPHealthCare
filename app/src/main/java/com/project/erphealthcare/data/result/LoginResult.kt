package com.project.erphealthcare.data.result

import com.project.erphealthcare.data.model.LoginResponse


sealed class LoginResult {
    class Success(val loginResponse: LoginResponse) : LoginResult()
    class ApiError(val statusCode: Int) : LoginResult()
    object ServerError : LoginResult()

}