package com.example.notes.Views

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.room.Room
import com.example.notes.Data.Dao.NoteDao
import com.example.notes.Data.NoteDataBase
import com.example.notes.Data.Repositories.NoteRepository
import com.example.notes.Utils.RecyclerView.NoteModel
import com.example.notes.ViewModels.NoteViewModel
import com.example.notes.databinding.FragmentNoteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

@AndroidEntryPoint
class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteViewModel by viewModels()
    private var editNote = false
    private var noteModel: NoteModel? = null

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @Suppress("DEPRECATION")
    inline fun <reified P : Parcelable> Bundle.getParcelableValue(key: String): P? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            getParcelable(key, P::class.java)
        } else {
            getParcelable(key)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNoteBinding.inflate(inflater, container, false)

        val dataBundle = arguments
        noteModel = dataBundle?.getParcelableValue<NoteModel>("NOTE")
        if (noteModel != null) {
            setData(noteModel!!)
            editNote = true
        } else {
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd-MM-yyyy hh:mm a")
            binding.lastEditNote.text = "Creada"
            binding.timeNote.text = formatter.format(date)
        }

        binding.btnSave.setOnClickListener {
            saveNote()
        }

        return binding.root
    }

    private fun saveNote() {
        if (editNote) {
            updateNote()
            return
        }
        val text = binding.textNote.text.toString()
        if (text.isBlank())
            return

        var title = binding.titleNote.text.toString()
        if (title.isBlank()) {
            var length = 10
            if (text.length < length) {
                length = text.length
            }
            title = binding.textNote.text.toString().substring(0, length)
        }

        viewModel.saveNote(title,text)
        showDialog()
    }

    private fun updateNote() {
        val note = NoteModel(
            noteModel!!.id,
            binding.titleNote.text.toString(),
            binding.textNote.text.toString(),
            ""
        )
        viewModel.updateNote(note)
        showDialog()
    }

    private fun showDialog() {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle("Guardado")
            setMessage("La nota se han guardado correctamente.")
            setPositiveButton("Aceptar") { dialog, which ->
                // Regresar al Fragment anterior
                requireActivity().supportFragmentManager.popBackStack()
            }
        }.create().show()
    }

    private fun setData(noteModel: NoteModel) {
        binding.titleNote.setText(noteModel.title)
        binding.textNote.setText(noteModel.note)
        binding.lastEditNote.text = "Modificado el"
        binding.timeNote.text = noteModel.lastEdit
    }
}