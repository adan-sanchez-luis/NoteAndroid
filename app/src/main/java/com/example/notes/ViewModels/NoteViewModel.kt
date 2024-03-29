package com.example.notes.ViewModels

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.notes.Data.Repositories.NoteRepository
import com.example.notes.Utils.RecyclerView.NoteModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {
    private val _data = MutableStateFlow(NoteComponents())
    val data = _data.asStateFlow()

    data class NoteComponents(
        val noteModel: NoteModel? = null,
        val allNotes: List<NoteModel> = emptyList()
    )

    fun getNotes() {
        viewModelScope.launch {
            _data.update {
                it.copy(allNotes = repository.getNotes().toMutableList())
            }
        }
    }

    fun saveNote(title: String, text: String) {
        val note = NoteModel(
            0,
            title,
            text,
            ""
        )
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun updateNote(noteModel: NoteModel) {
        viewModelScope.launch {
            repository.updateNote(noteModel)
        }
    }

    fun deleteNote(noteModel: NoteModel) {
        viewModelScope.launch {
            repository.deleteNote(noteModel)
            _data.update { it.copy(allNotes = repository.getNotes().toMutableList()) }
        }
    }
}

/*
@Suppress("UNCHECKED_CAST")
class NoteViewModelFactoty(private val repository: NoteRepository):ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NoteViewModel(repository) as T
    }
}*/