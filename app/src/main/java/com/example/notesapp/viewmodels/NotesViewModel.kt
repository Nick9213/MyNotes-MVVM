package com.example.notesapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.notesapp.db.database.NotesDatabase
import com.example.notesapp.db.entities.Notes
import com.example.notesapp.repository.NotesRepository

class NotesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NotesRepository

    init {
        val dao = NotesDatabase.getDatabaseInstance(application).noteDao()
        repository = NotesRepository(dao)
    }

    fun addNotes(notes: Notes) {
        repository.insertNotes(notes)
    }

    fun getAllNotes(): LiveData<List<Notes>> = repository.getAllNotes()

    fun getHighPriorNotes(): LiveData<List<Notes>> = repository.getHighPriorNotes()
    fun getMediumPriorNotes(): LiveData<List<Notes>> = repository.getMediumPriorNotes()
    fun getLowPriorNotes(): LiveData<List<Notes>> = repository.getLowPriorNotes()
    fun getCreatedNotes(): LiveData<List<Notes>> = repository.getCreatedNotes()

    fun deleteNotes(id: Int) {
        repository.deleteNotes(id)
    }

    fun updateNote(notes: Notes) {
        repository.updateNote(notes)
    }
}