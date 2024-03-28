package com.example.notes.Data.Repositories

import com.example.notes.Data.Dao.NoteDao
import com.example.notes.Data.Entities.NoteEntity
import com.example.notes.Utils.RecyclerView.NoteModel
import com.example.notes.Utils.RecyclerView.toEntity
import java.text.SimpleDateFormat
import java.util.Calendar

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun insertNote(noteModel: NoteModel) {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm a")
        val noteEntity = NoteEntity(
            title = noteModel.title,
            note = noteModel.note,
            lastEdit = formatter.format(date)
        )
        noteDao.insetNote(noteEntity)
    }

    suspend fun updateNote(noteModel: NoteModel) {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm a")
        val noteEntity = NoteEntity(
            id = noteModel.id,
            title = noteModel.title,
            note = noteModel.note,
            lastEdit = formatter.format(date)
        )
        noteDao.insetNote(noteEntity)
    }

    suspend fun getNotes(): List<NoteModel> {
        val entity = noteDao.getAllNotes()
        return entity.map {
            NoteModel(
                id = it.id,
                title = it.title,
                note = it.note,
                lastEdit = it.lastEdit
            )
        }
    }

    suspend fun deleteNote(noteModel: NoteModel) {
        val noteEntity = noteModel.toEntity()
        noteDao.deleteNote(noteEntity)
    }
}