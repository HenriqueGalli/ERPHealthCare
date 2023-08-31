package com.project.erphealthcare.ui.paciente.alergias

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.project.erphealthcare.R

class ListagemAdapter(historicoMedico: ArrayList<String>) :
    RecyclerView.Adapter<ListagemAdapter.ViewHolder>() {
    val alergias = historicoMedico
    private val ALERGIA = "ALERGIA"

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
        holder.bind(alergias[position])
        holder.exclude.setOnClickListener {
            alergias.remove(alergias[position])
            notifyDataSetChanged()
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
        }
        holder.check.setOnClickListener {
            val imm =
                holder.edit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(holder.edit.windowToken, 0)
            holder.edit.visibility = View.VISIBLE
            holder.check.visibility = View.GONE
            val enteredText = holder.tvName.text.toString()
            alergias[holder.absoluteAdapterPosition] = enteredText
            holder.tvName.isEnabled = false
        }
        if (position == alergias.size-1 && alergias[position] == ALERGIA) {
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
        return alergias.size
    }

    fun addAlergia() {
        alergias.add(ALERGIA)
        notifyDataSetChanged()
    }
}