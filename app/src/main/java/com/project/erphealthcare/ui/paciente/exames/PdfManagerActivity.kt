package com.project.erphealthcare.ui.paciente.exames

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
