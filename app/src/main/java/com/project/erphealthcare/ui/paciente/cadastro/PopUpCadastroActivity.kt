package com.project.erphealthcare.ui.paciente.cadastro

import android.content.Context
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
import com.project.erphealthcare.data.model.Agendamento
import com.project.erphealthcare.ui.login.LoginActivity
import com.project.erphealthcare.ui.paciente.agenda.AgendaActivity
import com.project.erphealthcare.ui.paciente.home.HomePacienteActivity


class PopUpCadastroActivity(
    private val isError: Boolean,
    private val isNewUser: Boolean = true,
    private val token: String = "",
    private val isExcludedMessage: Boolean = false,
    private val isAgenamento: Boolean = false,
    context: Context
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
        if (isNewUser) setPopUpStatus()
        else if (isExcludedMessage) setPopUpExcludeStatus()
        else if (isAgenamento) setupAgendamentoListener()
        else setPopUpStatusEditUser()

    }

    private fun setPopUpExcludeStatus() {
        setupExcludedListener()
        if (isError) {
            view?.findViewById<TextView>(R.id.popup_window_text)?.text =
                "Não foi possível excluir o usuário, tente novamente mais tarde"
            view?.findViewById<TextView>(R.id.popup_window_title)?.text = "Ops!"
        } else {
            view?.findViewById<TextView>(R.id.popup_window_text)?.text =
                "A exclusão foi realizada com sucesso"
            view?.findViewById<TextView>(R.id.popup_window_title)?.text = "Sucesso!"
        }
    }

    @RequiresApi(33)
    private fun setPopUpStatusEditUser() {
        setupEditListener()
        if (isError) {
            view?.findViewById<TextView>(R.id.popup_window_text)?.text =
                "Não foi possível realizar a edição, tente novamente mais tarde"
            view?.findViewById<TextView>(R.id.popup_window_title)?.text = "Ops!"
        } else {
            view?.findViewById<TextView>(R.id.popup_window_text)?.text =
                "A edição foi realizada com sucesso"
            view?.findViewById<TextView>(R.id.popup_window_title)?.text = "Sucesso!"
        }
    }

    @RequiresApi(33)
    private fun setupEditListener() {
        view?.findViewById<Button>(R.id.popup_window_button)?.setOnClickListener {
            if (isError) {
                dialog?.dismiss()
            } else {
                val intent = Intent(context, HomePacienteActivity::class.java)
                intent.putExtra("TOKEN", token)
                activity?.finish()
                startActivity(intent)
            }
        }
    }
    private fun setupAgendamentoListener() {
        view?.findViewById<Button>(R.id.popup_window_button)?.setOnClickListener {
            if (isError) {
                dialog?.dismiss()
            } else {
                val intent = Intent(context, AgendaActivity::class.java)
                activity?.finish()
                startActivity(intent)
            }
        }
    }

    private fun setupListener() {
        view?.findViewById<Button>(R.id.popup_window_button)?.setOnClickListener {
            if (isError) {
                dialog?.dismiss()
            } else {
                val intent = Intent(context, LoginActivity::class.java)
                activity?.finish()
                startActivity(intent)
            }
        }
    }

    private fun setupExcludedListener() {
        view?.findViewById<Button>(R.id.popup_window_button)?.setOnClickListener {
            if (isError) {
                dialog?.dismiss()
            } else {
                val intent = Intent(context, LoginActivity::class.java)
                activity?.finish()
                startActivity(intent)
            }

        }
    }

    private fun setPopUpStatus() {
        setupListener()
        if (isError) {
            view?.findViewById<TextView>(R.id.popup_window_text)?.text =
                "Não foi possível realizar o cadastro, tente novamente mais tarde"
            view?.findViewById<TextView>(R.id.popup_window_title)?.text = "Ops!"
        } else {
            view?.findViewById<TextView>(R.id.popup_window_text)?.text =
                "Seu cadastro foi realizado com sucesso"
            view?.findViewById<TextView>(R.id.popup_window_title)?.text = "Sucesso!"
        }
    }
}