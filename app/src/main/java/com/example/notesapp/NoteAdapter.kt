package com.example.notesapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class NoteAdapter(private var notes: List<NoteItems>, context: Context) :
    RecyclerView.Adapter<NoteAdapter.NoteVH>() {

    private var dbHelper: NoteDBHelper = NoteDBHelper(context)

    class NoteVH(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val TVtitle: TextView = itemview.findViewById(R.id.TVtitle)
        val TVdesc: TextView = itemview.findViewById(R.id.TVdesc)
        val BtnUpdate: ImageView = itemview.findViewById(R.id.BtnUpdate)
        val BtnDelete: ImageView = itemview.findViewById(R.id.BtnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_layout, parent, false)
        return NoteVH(view)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteVH, position: Int) {
        val note = notes[position]
        holder.TVtitle.text = note.title
        holder.TVdesc.text = note.desc

        holder.BtnUpdate.setOnClickListener {
            val intent = Intent(holder.itemView.context,UpdateNote::class.java).apply {
                putExtra("note_id",note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.BtnDelete.setOnClickListener {
            dbHelper.deleteNote(note.id)
            Toast.makeText(holder.itemView.context, "Note Deleted!!", Toast.LENGTH_SHORT).show()
            refreshNewNote(dbHelper.showNotes())
        }
    }

    fun refreshNewNote(newNotes: List<NoteItems>) {
        notes = newNotes
        notifyDataSetChanged()
    }

}