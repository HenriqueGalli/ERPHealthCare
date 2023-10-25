package com.project.erphealthcare.ui.cuidador.gerenciaPacientes.listaPacientes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.Paciente
import com.project.erphealthcare.ui.paciente.home.HomePacienteActivity

class ListaPacientesAdapter(
    pacientesList: ArrayList<Paciente>,
    isRemovePaciente: Boolean,
    onClickListener: OnRemovePaciente,
    token: String
) :
    RecyclerView.Adapter<ListaPacientesAdapter.PacienteViewHolder>() {

    val pacientes = pacientesList
    val tokenReceived = token
    val removePaciente = isRemovePaciente
    val listener = onClickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PacienteViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_paciente, parent, false)
        return PacienteViewHolder(itemView)
    }

    override fun getItemCount() = pacientes.size

    override fun onBindViewHolder(holder: PacienteViewHolder, position: Int) {
        val paciente = pacientes[position]
        holder.bind(paciente)
    }

    inner class PacienteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pacienteName: TextView = itemView.findViewById(R.id.tv_paciente_nome)
        val pacienteEmail: TextView = itemView.findViewById(R.id.tv_paciente_email)
        val button: ImageView = itemView.findViewById(R.id.btnSeePaciente)
        fun bind(paciente: Paciente) {
            pacienteName.text = paciente.nome
            pacienteEmail.text = paciente.email

            if (removePaciente)
                button.setImageResource(R.drawable.baseline_person_remove)

            button.setOnClickListener {
                if (removePaciente) {
                    val builder = AlertDialog.Builder(pacienteName.context)
                    builder.setMessage("Você realmente quer excluir esse paciente da sua lista?")
                        .setCancelable(false)
                        .setPositiveButton("Sim") { dialog, id ->
                            listener.onClick(paciente.email ?: "", paciente.cpf ?: "")
                        }
                        .setNegativeButton("Não") { dialog, id ->
                            dialog.dismiss()
                        }
                    val alert = builder.create()
                    alert.show()
                }
                else{
                    val intent = Intent(button.context, HomePacienteActivity::class.java)
                    intent.putExtra("VISAO_CUIDADOR_PACIENTE", paciente)
                    intent.putExtra("TOKEN", tokenReceived)
                    button.context.startActivity(intent)
                }
            }
        }

    }
}
