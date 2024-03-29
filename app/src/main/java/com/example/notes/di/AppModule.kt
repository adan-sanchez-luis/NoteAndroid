package com.example.notes.di

import android.app.Application
import androidx.room.Room
import com.example.notes.Data.Dao.NoteDao
import com.example.notes.Data.NoteDataBase
import com.example.notes.Data.Repositories.NoteRepository
import com.example.notes.ViewModels.NoteViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val DB_NAME = "Note_DB"

    @Provides
    @Singleton
    fun provideNoteDao(application: Application): NoteDao {
        val db = Room.databaseBuilder(
            application,
            NoteDataBase::class.java,
            DB_NAME
        ).build()
        return db.noteDao()
    }

    @Provides
    @Singleton
    fun provideRepository(noteDao: NoteDao): NoteRepository = NoteRepository(noteDao)

    @Provides
    @Singleton
    fun prvideNoteViewModel(noteRepository: NoteRepository): NoteViewModel =
        NoteViewModel(noteRepository)
}