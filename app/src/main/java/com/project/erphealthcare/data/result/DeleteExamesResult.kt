package com.project.erphealthcare.data.result

import com.google.gson.internal.LinkedTreeMap
import com.project.erphealthcare.data.model.Exame
sealed class DeleteExamesResult {
    object Success : DeleteExamesResult()
    object ServerError : DeleteExamesResult()
}