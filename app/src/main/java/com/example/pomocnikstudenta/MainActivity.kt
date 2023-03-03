package com.example.pomocnikstudenta

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pomocnikstudenta.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.FirstOptionButton.setOnClickListener {
            val intent = Intent(applicationContext, NoteActivity::class.java)
            startActivity(intent)
        }

        binding.SecondOptionButton.setOnClickListener {

            val intent = Intent(applicationContext, ScanActivity::class.java)
            startActivity(intent)

        }

        binding.ThirdOptionButton.setOnClickListener {
            val intent = Intent(applicationContext, PDFGeneratorActivity::class.java)
            startActivity(intent)
        }

    }
}