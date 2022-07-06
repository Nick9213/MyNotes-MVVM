package com.example.notesapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesapp.db.entities.Notes

@Dao
interface NotesDao {
    @Query("SELECT * FROM Notes")
    fun getNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority=1")
    fun getHighPriorityNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority=2")
    fun getMediumPriorityNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes WHERE priority=3")
    fun getLowPriorityNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes  Order by createdDate")
    fun getCreatedDateNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes  Order by createdDate ASC")
    fun getCreatedASCNotes(): LiveData<List<Notes>>

    @Query("SELECT * FROM Notes  Order by createdDate DESC")
    fun getCreatedDESCNotes(): LiveData<List<Notes>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(notes: Notes)

    @Query("DELETE FROM Notes WHERE id=:id")
    fun deleteNote(id: Int)

    @Update
    fun updateNotes(note: Notes)
}