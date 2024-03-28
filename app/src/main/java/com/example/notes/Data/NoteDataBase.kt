package com.example.notes.Data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.Data.Dao.NoteDao
import com.example.notes.Data.Entities.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
abstract class NoteDataBase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}