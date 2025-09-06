package com.example.notesapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDBHelper(context: Context) : SQLiteOpenHelper(
    context, DATABASE_NAME, null,
    DATABASE_VERSION
) {

    companion object {
        private const val DATABASE_NAME = "Notes.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "notes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createQry =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT);"
        db?.execSQL(createQry)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropQry = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropQry)
        onCreate(db)
    }

    fun insertNote(note: NoteItems) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_DESCRIPTION, note.desc)
        }

        db.insert(TABLE_NAME, null, values)
        db.close()

    }

    fun showNotes(): List<NoteItems> {
        val db = readableDatabase
        val noteList = mutableListOf<NoteItems>()
        val selectQry = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectQry, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

            val note = NoteItems(id, title, desc)
            noteList.add(note)

        }
        db.close()
        cursor.close()

        return noteList

    }

    fun updateNote(note: NoteItems) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_DESCRIPTION, note.desc)
        }
        val clause = "$COLUMN_ID = ?"
        val args = arrayOf(note.id.toString())
        db.update(TABLE_NAME, values, clause, args)
        db.close()
    }

    fun getNoteByID(noteID: Int) : NoteItems{
        val db = readableDatabase
        val qry = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteID"
        val cursor = db.rawQuery(qry,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))

        cursor.close()
        db.close()
        return NoteItems(id,title,desc)
    }

    fun deleteNote(noteID: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteID.toString())

        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()

    }

}