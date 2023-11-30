package com.project.erphealthcare.data.result

sealed class CreateAgendamentoResult{
    object Success : CreateAgendamentoResult()
    class ApiError(val statusCode: Int) : CreateAgendamentoResult()
    object ServerError : CreateAgendamentoResult()
}
