package com.project.erphealthcare.ui.paciente.exames

import com.project.erphealthcare.data.model.Exame

interface onUpdateExame {
    fun exclude(exame: Exame)
    fun update(exame: Exame)
}