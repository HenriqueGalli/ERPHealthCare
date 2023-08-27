package com.project.erphealthcare.ui.paciente.alergias

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.erphealthcare.R

class ListagemAdapter(historicoMedico: ArrayList<String>) :
    RecyclerView.Adapter<ListagemAdapter.ViewHolder>() {
    val list = historicoMedico

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_label_name)
        val exclude = view.findViewById<ImageView>(R.id.exclude)


        fun bind(historico: String) {
            tvName.text = historico
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.listagem_historico, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
        holder.exclude.setOnClickListener {
            list.remove(list[position])
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}