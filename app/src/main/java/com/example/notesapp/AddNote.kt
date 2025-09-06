package com.example.notesapp

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Note
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AddNote : AppCompatActivity() {

    private lateinit var db: NoteDBHelper
    private lateinit var title: EditText
    private lateinit var desc: EditText
    private lateinit var save: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_note)

        title = findViewById(R.id.etTitle)
        desc = findViewById(R.id.etDescription)
        save = findViewById(R.id.BtnSave)

        db = NoteDBHelper(this)

        save.setOnClickListener {
            val notetitle = title.text.toString()
            val notedesc = desc.text.toString()
            val note = NoteItems(1, notetitle, notedesc)

            if (title.text.isEmpty()) {
                title.error = "Please Enter Some Title of The Note!"
            } else if (desc.text.isEmpty()) {
                desc.error = "Please Write Some Description of The Note!!"
            } else {
                db.insertNote(note)
                finish()
                Toast.makeText(this, "Note Added!!", Toast.LENGTH_SHORT).show()
            }

        }

    }
}