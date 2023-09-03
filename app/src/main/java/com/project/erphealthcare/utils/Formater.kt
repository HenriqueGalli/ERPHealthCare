package com.project.erphealthcare.utils

import java.text.SimpleDateFormat
import java.util.*

object Formater {

    fun formatarDataEHora(dataString: String): String {
        val formatoEntrada = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val formatoSaida = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())

        try {
            val data = formatoEntrada.parse(dataString)
            return formatoSaida.format(data)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}