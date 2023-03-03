package com.example.pomocnikstudenta

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.pomocnikstudenta.databinding.ActivityScanBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

class ScanActivity : AppCompatActivity() {

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    lateinit var binding: ActivityScanBinding

    private val REQUEST_IMAGE_CAPTURE = 1

    private var imageBitmap: Bitmap? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_scan)

        binding.apply {

            zdjecie.setOnClickListener {

                takeImage()

                textPDF.text = ""
            }

            skanuj.setOnClickListener {

                processImage()
            }


        }

        binding.zapisz.setOnClickListener {
            val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

            val clip = ClipData.newPlainText("TextView", binding.textPDF.getText().toString())

            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, "skopiowano tekst", Toast.LENGTH_SHORT).show()
        }


    }


    private fun takeImage() {

        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
        } catch (e: Exception) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            val extras: Bundle? = data?.extras

            imageBitmap = extras?.get("data") as Bitmap

            if (imageBitmap != null) {

                binding.imageView.setImageBitmap(imageBitmap)
            }
        }
    }

    private fun processImage() {

        if (imageBitmap != null) {

            val image = imageBitmap?.let {

                InputImage.fromBitmap(it, 0)

            }

            image?.let {
                recognizer.process(it)
                    .addOnSuccessListener { visionText ->

                        binding.textPDF.text = visionText.text

                    }
                    .addOnFailureListener { e ->
                    }
            }
        } else {
            Toast.makeText(this, "Proszę wybrać zdjęcie", Toast.LENGTH_SHORT).show()
        }
    }
}