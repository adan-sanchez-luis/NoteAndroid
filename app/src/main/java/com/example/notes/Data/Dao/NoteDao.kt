package com.example.notes.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notes.Data.Entities.NoteEntity

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table")
    suspend fun getAllNotes(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetNote(noteEntity: NoteEntity)

    @Delete(entity = NoteEntity::class)
    suspend fun deleteNote(noteEntity: NoteEntity)
}