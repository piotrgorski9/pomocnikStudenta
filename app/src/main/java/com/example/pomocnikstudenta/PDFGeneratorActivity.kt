package com.example.pomocnikstudenta

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pomocnikstudenta.databinding.ActivityPdfgeneratorBinding
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class PDFGeneratorActivity : AppCompatActivity() {

    lateinit var binding: ActivityPdfgeneratorBinding
    private val STORAGE_CODE = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfgeneratorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPDF.setOnClickListener {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED
                ) {
                    val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, STORAGE_CODE)
                } else {
                    savePDF()
                }
            } else {
                savePDF()
            }

        }
    }

    private fun savePDF() {
        val mDoc = Document()
        val mFileName = SimpleDateFormat("yyyyMMdd_HHmmmss", Locale.getDefault())
            .format(System.currentTimeMillis())

        val mFilePatch =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .toString() + "/" + mFileName + ".pdf"

        try {

            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePatch))
            mDoc.open()

            val data = binding.textPDF.text.toString().trim()
            mDoc.addAuthor("Ja")
            mDoc.add(Paragraph(data))
            mDoc.close()
            Toast.makeText(this, "$mFileName.pdf\n stworzono \n$mFilePatch", Toast.LENGTH_SHORT)
                .show()

        } catch (e: Exception) {
            Toast.makeText(this, "" + e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    savePDF()
                } else {
                    Toast.makeText(this, "Odmowa pozwolenia!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
