package com.example.notes.Utils.RecyclerView

import android.os.Parcel
import android.os.Parcelable
import com.example.notes.Data.Entities.NoteEntity


data class NoteModel(
    val id: Int,
    val title: String,
    val note: String,
    val lastEdit: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(note)
        parcel.writeString(lastEdit)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteModel> {
        override fun createFromParcel(parcel: Parcel): NoteModel {
            return NoteModel(parcel)
        }

        override fun newArray(size: Int): Array<NoteModel?> {
            return arrayOfNulls(size)
        }
    }
}

fun NoteModel.toEntity(): NoteEntity {
    return NoteEntity(
        id = this.id,
        title = this.title,
        note = this.note,
        lastEdit = this.lastEdit
    )
}