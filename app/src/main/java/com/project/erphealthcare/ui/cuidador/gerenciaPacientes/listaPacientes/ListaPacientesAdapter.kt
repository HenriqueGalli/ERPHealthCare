package com.project.erphealthcare.ui.cuidador.gerenciaPacientes.listaPacientes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.Paciente

class ListaPacientesAdapter(pacientesList: ArrayList<Paciente>) :
    RecyclerView.Adapter<ListaPacientesAdapter.PacienteViewHolder>() {

    val pacientes = pacientesList
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
        val pacienteDocument: TextView = itemView.findViewById(R.id.tv_paciente_documento)
        fun bind(paciente: Paciente) {
            pacienteName.text = paciente.nome
            pacienteDocument.text = paciente.cpf
        }

    }
}
