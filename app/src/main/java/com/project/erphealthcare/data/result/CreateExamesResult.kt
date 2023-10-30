package com.project.erphealthcare.data.result

import com.google.gson.internal.LinkedTreeMap
import com.project.erphealthcare.data.model.Exame
sealed class CreateExamesResult {
    data class Success(val exame: LinkedTreeMap<String,Any>) : CreateExamesResult()
    object ServerError : CreateExamesResult()
}