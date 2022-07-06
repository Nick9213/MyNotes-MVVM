package com.example.notesapp.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Notes")
@Parcelize
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val title: String,
    val subtitle: String,
    val description: String,
    val createdDate: String,
    val priority: String

/*    @PrimaryKey(autoGenerate = true)
    val id: String? = null,
    val title: String? = null,
    val subtitle: String? = null,
    val description: String? = null,
    val createdDate: String? = null,
    val priority: String? = null,*/
) : Parcelable