package br.com.preventivewelfare.api.result

sealed class DeleteUserResult {
    object Success : DeleteUserResult()
    object ServerError : DeleteUserResult()
}
