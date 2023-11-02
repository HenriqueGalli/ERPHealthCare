package com.project.erphealthcare.ui.paciente.exames

import android.content.Context
import android.content.Intent
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.project.erphealthcare.R
import com.project.erphealthcare.data.model.Exame


class ExamAdapter(examList: List<Exame>, updateExame: onUpdateExame) :
    RecyclerView.Adapter<ExamAdapter.ExamViewHolder>() {

    val exames: ArrayList<Exame> = examList as ArrayList<Exame>
    val updateExameListener = updateExame
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
        val examNameTextView: EditText = itemView.findViewById(R.id.examNameTextView)
        val exclude: ImageView = itemView.findViewById(R.id.exclude)
        val edit: ImageView = itemView.findViewById(R.id.edit)
        val check: ImageView = itemView.findViewById(R.id.check)
        val detail: ImageView = itemView.findViewById(R.id.detail)

        fun bind(exam: Exame) {
            examNameTextView.setText(exam.nomeExame)
            detail.setOnClickListener {
                val intent = Intent(examNameTextView.context, PdfManagerActivity::class.java)
                val byte = base64ToByteArray(exam.arquivoExame)
                intent.putExtra("PDF", byte)
                intent.putExtra("PDF_NOME", exam.nomeExame)
                examNameTextView.context.startActivity(intent)
            }
            exclude.setOnClickListener {
                val builder = AlertDialog.Builder(exclude.context)
                builder.setMessage("Você realmente quer deletar o exame?")
                    .setCancelable(false)
                    .setPositiveButton("Sim") { dialog, id ->
                        updateExameListener.exclude(exam)
                    }
                    .setNegativeButton("Não") { dialog, id ->
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
            edit.setOnClickListener {
                val imm =
                    edit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                examNameTextView.isEnabled = true
                examNameTextView.requestFocus()
                edit.visibility = View.GONE
                check.visibility = View.VISIBLE
                examNameTextView.setSelection(examNameTextView.text.length)
            }
            check.setOnClickListener {
                val imm =
                    edit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(edit.windowToken, 0)
                edit.visibility = View.VISIBLE
                check.visibility = View.GONE
                val enteredText = examNameTextView.text.toString()
                exames[absoluteAdapterPosition].nomeExame = enteredText
                examNameTextView.isEnabled = false
                updateExameListener.update(exam)
            }
        }
    }

    private fun base64ToByteArray(base64String: String): ByteArray {
        return Base64.decode(base64String, Base64.DEFAULT)
    }

    fun addExame(exame: Exame) {
        exames.add(exame)
        notifyDataSetChanged()
    }
}
