package com.project.erphealthcare.data.result

sealed class CreatePacienteResult{
    object Success : CreatePacienteResult()
    class ApiError(val statusCode: Int) : CreatePacienteResult()
    object ServerError : CreatePacienteResult()
}
