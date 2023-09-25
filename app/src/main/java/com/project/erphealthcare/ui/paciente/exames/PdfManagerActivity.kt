package com.project.erphealthcare.ui.paciente.exames

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.github.barteksc.pdfviewer.PDFView

import com.project.erphealthcare.R
import java.io.File
import java.io.FileOutputStream

class PdfManagerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_view)


        if(intent.hasExtra("PDF")){
            val pdfArray = intent.getByteArrayExtra("PDF")
            val pdfNome = intent.getByteArrayExtra("PDF_NOME")
            if (pdfArray != null) {
                setupLocalPDF(pdfArray)
                setupListeners()
            }
        }
    }

    private fun setupListeners() {
        val shareButton = findViewById<Button>(R.id.btnBuscarExame)
        shareButton.setOnClickListener {
            val tempFile = File(cacheDir, "temp.pdf")

            // Verifique se o arquivo temporário existe
            if (tempFile.exists()) {
                // Crie uma Uri para o arquivo usando FileProvider
                val uri = FileProvider.getUriForFile(
                    this,
                    applicationContext.packageName + ".provider",
                    tempFile
                )

                // Crie uma Intent para compartilhar o arquivo PDF
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.type = "application/pdf"
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                // Inicie a atividade de compartilhamento
                startActivity(Intent.createChooser(shareIntent, "Compartilhar PDF"))
            }
        }
    }

    private fun setupLocalPDF(pdfArray: ByteArray) {
        val tempFile = File(cacheDir, "temp.pdf")

        try {
            val outputStream = FileOutputStream(tempFile)
            outputStream.write(pdfArray)
            outputStream.close()
            val pdfView = findViewById<PDFView>(R.id.pdfView)
            // Carregue e exiba o PDF no PDFView
            pdfView.fromFile(tempFile)
                .defaultPage(0) // Página inicial
                .enableSwipe(true) // Ativar deslizamento horizontal
                .swipeHorizontal(false)
                .enableDoubletap(true) // Ativar duplo toque para zoom
                .load()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
