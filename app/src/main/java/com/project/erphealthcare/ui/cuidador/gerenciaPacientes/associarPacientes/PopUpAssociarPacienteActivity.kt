package com.project.erphealthcare.ui.cuidador.gerenciaPacientes.associarPacientes.associarPaciente

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.project.erphealthcare.R
import com.project.erphealthcare.ui.cuidador.home.HomeCuidadorActivity


class PopUpAssociarPacienteActivity(
    private val isError: Boolean,
    private val token: String
) :
    DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(
            R.layout.popup_window_text,
            container,
            false
        )

    }

    @RequiresApi(33)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPopUpStatus()
    }


    private fun setupListener() {
        view?.findViewById<Button>(R.id.popup_window_button)?.setOnClickListener {
            if (isError) {
                dialog?.dismiss()
            } else {
                val intent = Intent(context, HomeCuidadorActivity::class.java)
                intent.putExtra("TOKEN", token)
                activity?.finish()
                startActivity(intent)
            }
        }
    }

    private fun setPopUpStatus() {
        setupListener()
        if (isError) {
            view?.findViewById<TextView>(R.id.popup_window_text)?.text =
                "Não foi possível realizar a associação do paciente, tente novamente mais tarde"
            view?.findViewById<TextView>(R.id.popup_window_title)?.text = "Ops!"
        } else {
            view?.findViewById<TextView>(R.id.popup_window_text)?.text =
                "A associação do paciente foi realizada com sucesso"
            view?.findViewById<TextView>(R.id.popup_window_title)?.text = "Sucesso!"
        }
    }
}