package com.project.erphealthcare.ui.paciente.exames

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.erphealthcare.data.model.Exame
import com.project.erphealthcare.databinding.ActivityListaExamesBinding
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class ListaExamesActivity : AppCompatActivity() {

    private val PICK_PDF_REQUEST = 1

    private lateinit var binding: ActivityListaExamesBinding
    private lateinit var adapter: ExamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaExamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupAdapter()
        setupListeners()
    }

    private fun setupAdapter() {
        val examList = arrayListOf<Exame>()
        adapter = ExamAdapter(examList)
        binding.examRecyclerView.adapter = adapter
        binding.examRecyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupListeners() {
        binding.btnBuscarExame.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"
            startActivityForResult(intent, PICK_PDF_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val pdfUri: Uri? = data.data
            if (pdfUri != null) {
                val pdfFileName = getFileNameFromUri(pdfUri) ?: ""
                try {
                    val inputStream: InputStream? = contentResolver.openInputStream(pdfUri)
                    if (inputStream != null) {
                        val byteArray = readBytes(inputStream)
                        adapter.addExame(Exame(pdfFileName, byteArray))
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(this, "Erro ao ler o arquivo PDF", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun readBytes(inputStream: InputStream): ByteArray {
        val byteBuffer = ByteArrayOutputStream()
        val bufferSize = 1024
        val buffer = ByteArray(bufferSize)
        var len: Int
        while (inputStream.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
        return byteBuffer.toByteArray()
    }

    @SuppressLint("Range")
    private fun getFileNameFromUri(uri: Uri): String? {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val displayName =
                    it.getString(it.getColumnIndex(android.provider.OpenableColumns.DISPLAY_NAME))
                it.close()
                return displayName
            }
            it.close()
        }
        return null
    }
}