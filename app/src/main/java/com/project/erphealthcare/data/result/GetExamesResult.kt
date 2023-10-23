package com.project.erphealthcare.data.result

import com.google.gson.internal.LinkedTreeMap
import com.project.erphealthcare.data.model.Exame
sealed class GetExamesResult {
    data class Success(val exames: ArrayList<LinkedTreeMap<String,Any>>) : GetExamesResult()
    object ServerError : GetExamesResult()
}