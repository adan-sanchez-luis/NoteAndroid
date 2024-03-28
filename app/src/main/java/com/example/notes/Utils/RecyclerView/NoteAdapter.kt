package com.example.notes.Utils.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.databinding.CardviewNotesBinding

class NoteAdapter(
    private var list: List<NoteModel>,
    private val onItemClick: (NoteModel) -> Unit,
    private val onItemLongClick: (NoteModel) -> Unit
) : RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.cardview_notes, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.createNote(item, onItemClick,onItemLongClick)
    }

    fun search(listSearch: List<NoteModel>) {
        list = listSearch
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val biding = CardviewNotesBinding.bind(view)

        fun createNote(note: NoteModel, selectItem: (NoteModel) -> Unit, longSelectItem: (NoteModel) -> Unit) {
            biding.tvTitle.text = note.title
            biding.tvNote.text = note.note
            biding.tvDate.text = note.lastEdit
            itemView.setOnClickListener {
                selectItem(note)
                true
            }
            itemView.setOnLongClickListener {
                longSelectItem(note)
                true
            }
        }
    }

}