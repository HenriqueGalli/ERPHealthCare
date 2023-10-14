package com.project.erphealthcare.data.result

sealed class CreateCuidadorResult{
    object Success : CreateCuidadorResult()
    class ApiError(val statusCode: Int) : CreateCuidadorResult()
    object ServerError : CreateCuidadorResult()
}
