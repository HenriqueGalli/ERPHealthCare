package com.project.erphealthcare.data.result

import com.project.erphealthcare.data.model.Cuidador

sealed class GetCuidadorResult {
    data class Success(val user: Cuidador?) : GetCuidadorResult()
    object ServerError : GetCuidadorResult()

}
