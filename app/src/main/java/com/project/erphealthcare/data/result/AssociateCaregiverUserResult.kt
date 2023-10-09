package com.project.erphealthcare.data.result

sealed class AssociateCaregiverUserResult {
    object Success : AssociateCaregiverUserResult()
    object ServerError : AssociateCaregiverUserResult()
}
