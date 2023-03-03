package com.example.pomocnikstudenta

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pomocnikstudenta.Models.Note
import com.example.pomocnikstudenta.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var note: Note
    private lateinit var old_note: Note
    var isUpdate = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {

            old_note = intent.getSerializableExtra("current_note") as Note
            binding.editTitle.setText(old_note.title)
            binding.editNote.setText(old_note.note)
            isUpdate = true

        } catch (e: Exception) {

            e.printStackTrace()

        }

        binding.imgCheck.setOnClickListener {

            val title = binding.editTitle.text.toString()
            val note_podpis = binding.editNote.text.toString()

            if (title.isNotEmpty() || note_podpis.isNotEmpty()) {

                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if (isUpdate) {
                    note = Note(
                        old_note.id, title, note_podpis, formatter.format(Date())
                    )
                } else {

                    note = Note(
                        null, title, note_podpis, formatter.format(Date())

                    )

                }

                val intent = Intent()
                intent.putExtra("note", note)
                setResult(Activity.RESULT_OK, intent)
                finish()


            } else {

                Toast.makeText(this@AddNote, "Proszę wpisz coś", Toast.LENGTH_SHORT).show()
                return@setOnClickListener

            }

        }

        binding.imgBack.setOnClickListener {

            onBackPressed()

        }

    }
}