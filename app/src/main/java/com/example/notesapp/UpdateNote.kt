package com.example.notesapp

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class UpdateNote : AppCompatActivity() {

    private lateinit var dbHelper: NoteDBHelper
    private lateinit var updateTitleEditText: EditText
    private lateinit var updateDescEditText: EditText
    private lateinit var updateBtn : ImageView
    private var noteID : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_update_note)

        updateTitleEditText = findViewById(R.id.etUpdateTitle)
        updateDescEditText = findViewById(R.id.etUpdateDescription)
        updateBtn = findViewById(R.id.BtnUpdateSave)

        dbHelper = NoteDBHelper(this)

        noteID = intent.getIntExtra("note_id",-1)
        if(noteID == -1){
            finish()
            return
        }

        val note = dbHelper.getNoteByID(noteID)
        updateTitleEditText.setText(note.title)
        updateDescEditText.setText(note.desc)

        updateBtn.setOnClickListener {

            val newTitle = updateTitleEditText.text.toString()
            val newDesc = updateDescEditText.text.toString()
            val newNote = NoteItems(noteID,newTitle,newDesc)

            if(updateTitleEditText.text.isEmpty()){
                updateTitleEditText.error = "Enter Title First!"
            }
            else if (updateDescEditText.text.isEmpty()){
                updateDescEditText.error = "Enter Description for Note!"
            }
            else {
                dbHelper.updateNote(newNote)
                finish()
                Toast.makeText(this,"Note Edited!!",Toast.LENGTH_SHORT).show()
            }

        }


    }
}