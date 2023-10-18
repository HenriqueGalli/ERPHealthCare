package com.project.erphealthcare.ui.paciente.historicoMedico

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.project.erphealthcare.R
import com.project.erphealthcare.ui.cuidador.gerenciaPacientes.listaPacientes.OnRemovePaciente

class ListagemAdapter(historicoMedico: ArrayList<String>, onClickListener:onAddHistorico) :
    RecyclerView.Adapter<ListagemAdapter.ViewHolder>() {
    val historico = historicoMedico
    private val ALERGIA = "ALERGIA"
    val listener = onClickListener

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<EditText>(R.id.et_label_name)
        val edit = view.findViewById<ImageView>(R.id.edit)
        val check = view.findViewById<ImageView>(R.id.check)
        val exclude = view.findViewById<ImageView>(R.id.exclude)

        fun bind(historico: String) {
            tvName.setText(historico)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.listagem_historico, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(historico[position])
        holder.exclude.setOnClickListener {
            historico.remove(historico[position])
            notifyDataSetChanged()
            listener.setVisible()

        }
        holder.edit.setOnClickListener {
            val imm =
                holder.edit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            holder.tvName.isEnabled = true
            holder.tvName.requestFocus()
            holder.edit.visibility = View.GONE
            holder.check.visibility = View.VISIBLE
            holder.tvName.setSelection(holder.tvName.text.length)
            listener.setInvisible()

        }
        holder.check.setOnClickListener {
            val imm =
                holder.edit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(holder.edit.windowToken, 0)
            holder.edit.visibility = View.VISIBLE
            holder.check.visibility = View.GONE
            val enteredText = holder.tvName.text.toString()
            historico[holder.absoluteAdapterPosition] = enteredText
            holder.tvName.isEnabled = false
            listener.setVisible()

        }
        if (position == historico.size-1 && historico[position] == ALERGIA) {
            val imm =
                holder.edit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
            holder.tvName.isEnabled = true
            holder.tvName.setText("")
            holder.tvName.requestFocus()
            holder.edit.visibility = View.GONE
            holder.check.visibility = View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return historico.size
    }

    fun addItem() {
        historico.add(ALERGIA)
        notifyDataSetChanged()
    }
}