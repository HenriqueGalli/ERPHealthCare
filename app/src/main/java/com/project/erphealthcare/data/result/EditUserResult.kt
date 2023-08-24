package br.com.preventivewelfare.api.result

sealed class EditUserResult {
    object Success: EditUserResult()
    object ServerError : EditUserResult()
}
