package com.example.notesapp.repository

import androidx.lifecycle.LiveData
import com.example.notesapp.db.dao.NotesDao
import com.example.notesapp.db.entities.Notes

class NotesRepository(private val dao: NotesDao) {

    fun getAllNotes(): LiveData<List<Notes>> {
        return dao.getNotes()
    }

    fun getHighPriorNotes(): LiveData<List<Notes>> {
        return dao.getHighPriorityNotes()
    }

    fun getMediumPriorNotes(): LiveData<List<Notes>> {
        return dao.getMediumPriorityNotes()
    }

    fun getLowPriorNotes(): LiveData<List<Notes>> {
        return dao.getLowPriorityNotes()
    }

    fun getCreatedNotes(): LiveData<List<Notes>> {
        return dao.getCreatedDateNotes()
    }

    fun insertNotes(notes: Notes) {
        return dao.insertNote(notes)
    }

    fun deleteNotes(id: Int) {
        return dao.deleteNote(id)
    }

    fun updateNote(notes: Notes) {
        return dao.updateNotes(notes)
    }


}