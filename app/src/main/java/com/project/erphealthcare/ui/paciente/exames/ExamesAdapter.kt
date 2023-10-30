package com.project.erphealthcare.ui.paciente.exames

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.Exame


class ExamAdapter(examList: List<Exame>) :
    RecyclerView.Adapter<ExamAdapter.ExamViewHolder>() {

    val exames: ArrayList<Exame> = examList as ArrayList<Exame>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_exame, parent, false)
        return ExamViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
        val exam = exames[position]
        holder.bind(exam)
    }

    override fun getItemCount(): Int {
        return exames.size
    }

    inner class ExamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val examNameTextView: TextView = itemView.findViewById(R.id.examNameTextView)

        fun bind(exam: Exame) {
            examNameTextView.text = exam.nomeExame
            examNameTextView.setOnClickListener {
                val intent = Intent(examNameTextView.context, PdfManagerActivity::class.java)
                intent.putExtra("PDF", exam.arquivoExame)
                intent.putExtra("PDF_NOME", exam.nomeExame)
                examNameTextView.context.startActivity(intent)
            }
        }
    }

    fun addExame(exame: Exame) {
        exames.add(exame)
        notifyDataSetChanged()
    }
}
