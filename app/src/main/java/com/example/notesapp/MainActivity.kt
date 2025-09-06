package com.example.notesapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: NoteDBHelper
    private lateinit var adapter: NoteAdapter
    private lateinit var notelist : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        notelist = findViewById(R.id.NotesReView)

        dbHelper = NoteDBHelper(this)

        adapter = NoteAdapter(dbHelper.showNotes(),this)

        notelist.layoutManager = LinearLayoutManager(this)
        notelist.adapter = adapter

        val addNote = findViewById<FloatingActionButton>(R.id.BtnAddNote)

        addNote.setOnClickListener {
            val intent = Intent(this, AddNote::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        adapter.refreshNewNote(dbHelper.showNotes())
    }
}