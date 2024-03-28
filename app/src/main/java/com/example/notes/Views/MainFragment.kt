package com.example.notes.Views

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.notes.Data.Dao.NoteDao
import com.example.notes.Data.NoteDataBase
import com.example.notes.Data.Repositories.NoteRepository
import com.example.notes.R
import com.example.notes.Utils.RecyclerView.NoteAdapter
import com.example.notes.Utils.RecyclerView.NoteModel
import com.example.notes.databinding.FragmentMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    val binding get() = _binding!!
    private var db: NoteDataBase? = null
    private var dao: NoteDao? = null
    private var repository: NoteRepository? = null
    private lateinit var listNotes: MutableList<NoteModel>
    private lateinit var notesAdapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        db = Room.databaseBuilder(requireContext(), NoteDataBase::class.java, "Note_DB").build()
        dao = db!!.noteDao()
        repository = NoteRepository(dao!!)

        MainScope().launch {
            listNotes = repository!!.getNotes().toMutableList()
            binding.allNotes.layoutManager = LinearLayoutManager(context)
            notesAdapter = NoteAdapter(listNotes, onItemClick = { NoteModel ->
                onItemClick(NoteModel)
            }, onItemLongClick = { NoteModel ->
                onItemLongClick(NoteModel)
            })
            binding.allNotes.adapter = notesAdapter
        }

        binding.addNote.setOnClickListener {
            val newNote = NoteFragment()
            val manager = requireActivity().supportFragmentManager
            val fragmentTransaction = manager.beginTransaction()
            fragmentTransaction.replace(R.id.FragmentOrigin, newNote)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        return binding.root
    }

    private fun onItemClick(noteModel: NoteModel) {
        val dataBundle = Bundle().apply {
            putParcelable("NOTE", noteModel)
        }
        val newNote = NoteFragment().apply {
            arguments = dataBundle
        }
        val manager = requireActivity().supportFragmentManager
        val fragmentTransaction = manager.beginTransaction()
        fragmentTransaction.replace(R.id.FragmentOrigin, newNote)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun onItemLongClick(noteModel: NoteModel) {
        AlertDialog.Builder(requireContext())
            .setTitle("Eliminar la nota")
            .setMessage("¿Estás seguro de que quieres eliminar la nota ${noteModel.title}?")
            .setPositiveButton("Sí") { _, _ ->
                MainScope().launch {
                    repository!!.deleteNote(noteModel)
                    listNotes.remove(noteModel)
                    notesAdapter.notifyDataSetChanged()
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun editNote() {
    }
}