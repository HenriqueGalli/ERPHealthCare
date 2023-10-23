package com.project.erphealthcare.ui.paciente.exames

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.internal.LinkedTreeMap
import com.project.erphealthcare.data.api.ApiService
import com.project.erphealthcare.data.model.Exame
import com.project.erphealthcare.data.result.CreateExamesResult
import com.project.erphealthcare.data.result.GetExamesResult
import com.project.erphealthcare.databinding.ActivityListaExamesBinding
import com.project.erphealthcare.ui.paciente.home.HomePacienteActivity
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

class ListaExamesActivity : AppCompatActivity() {

    private val PICK_PDF_REQUEST = 1

    private lateinit var binding: ActivityListaExamesBinding
    private lateinit var adapter: ExamAdapter

    private val viewModel: ListaExamesViewModel =
        ListaExamesViewModelFactory()
            .create(ListaExamesViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListaExamesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupObserver()
        viewModel.getExamHistory()
        setupListeners()
    }

    private fun setupObserver() {
        viewModel.examesResult.observe(this) { res ->
            when (res) {
                is GetExamesResult.Success -> setupAdapter(res.exames)
                is GetExamesResult.ServerError -> setupAdapter(arrayListOf())
            }
        }

        viewModel.createExamesResult.observe(this) { res ->
            when(res) {
                is CreateExamesResult.Success -> viewModel.getExamHistory()
                is CreateExamesResult.ServerError -> errorCreateExam()
            }
        }
    }

    private fun errorCreateExam() {

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomePacienteActivity::class.java)
        intent.putExtra("TOKEN", ApiService.token)
        startActivity(intent)
        this.finish()
    }

    private fun setupAdapter(list: ArrayList<LinkedTreeMap<String,Any>>) {

        val mappedList : List<Exame> = list.map {createExameFromLinkedTreeMap(it)  }
        adapter = ExamAdapter(mappedList)
        binding.examRecyclerView.adapter = adapter
        binding.examRecyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL,
            false
        )
    }

    private fun setupListeners() {
        binding.btnBuscarExame.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            intent.putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("application/pdf", "image/png", "image/jpeg")
            )
            startActivityForResult(intent, PICK_PDF_REQUEST)
        }
        binding.btnSalvarExames.setOnClickListener {

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

                        var byteArray = readBytes(inputStream)
                        if (pdfFileName.contains(".png") || pdfFileName.contains(".jpeg")) {
                            byteArray = convertImageToPDF(pdfUri)
                        }
                        val exame = Exame(
                            nomeExame = pdfFileName,
                            arquivoExame = byteArray,
                            nomeMedico = "Dr",
                            dataExame = "",
                            id = "",
                            idUsuario = "",
                        )
                        //adapter.addExame(exame)
                        viewModel.createExam(exame =exame)
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

    fun convertImageToPDF(imageUri: Uri): ByteArray {
        val context = applicationContext // Substitua pela referência ao contexto adequado

        val bitmap: Bitmap
            // Carregue a imagem da URI
            val inputStream = context.contentResolver.openInputStream(imageUri)
            bitmap = BitmapFactory.decodeStream(inputStream)

        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        pdfDocument.finishPage(page)

            val outputStream = ByteArrayOutputStream()
            pdfDocument.writeTo(outputStream)
            pdfDocument.close()

            // Converta o documento PDF em um array de bytes
            return outputStream.toByteArray()
    }

    fun createExameFromLinkedTreeMap(linkedTreeMap: LinkedTreeMap<String, Any>): Exame {
        val id = linkedTreeMap["id"].toString()
        val nomeExame = linkedTreeMap["nomeExame"].toString()
        val arquivoExame = (linkedTreeMap["arquivoExame"] as String).toByteArray() // Converter a String para ByteArray
        val dataExame = linkedTreeMap["dataExame"].toString()
        val nomeMedico = linkedTreeMap["nomeMedico"].toString()
        val idUsuario = linkedTreeMap["idUsuario"].toString()

        return Exame(id, nomeExame, arquivoExame, dataExame, nomeMedico, idUsuario)
    }
}