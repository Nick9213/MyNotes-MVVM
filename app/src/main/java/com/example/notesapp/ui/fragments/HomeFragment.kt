package com.example.notesapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.adapters.NotesAdapter
import com.example.notesapp.databinding.FragmentHomeBinding
import com.example.notesapp.db.entities.Notes
import com.example.notesapp.viewmodels.NotesViewModel

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel: NotesViewModel by viewModels()
    private var oldNotesList = arrayListOf<Notes>()
    lateinit var adapter: NotesAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.rvAllNotes.setHasFixedSize(true)
        binding.rvAllNotes.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        getALlNotes()
        getSearchWidget()

        binding.addNoteButton.setOnClickListener {
            val data = Notes(null, "", "", "", "", "3")
            val action = HomeFragmentDirections.actionHomeFragmentToNotesFragment(data)
            Navigation.findNavController(it).navigate(action)
        }
        binding.filterHigh.setOnClickListener {
            viewModel.getHighPriorNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotesList = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rvAllNotes.adapter = adapter
            }
        }
        binding.filterMedium.setOnClickListener {
            viewModel.getMediumPriorNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotesList = notesList as ArrayList<Notes>
                adapter = NotesAdapter(requireContext(), notesList)
                binding.rvAllNotes.adapter = adapter
            }
        }
        binding.filterLow.setOnClickListener {
            viewModel.getLowPriorNotes().observe(viewLifecycleOwner) { notesList ->
                oldNotesList = notesList as ArrayList<Notes>
                binding.rvAllNotes.adapter = adapter
            }
        }
        binding.filterAllNotes.setOnClickListener {
            getALlNotes()
        }
        return binding.root
    }

    private fun getSearchWidget() {
        val searchView = binding.edSearchFilter
        searchView.queryHint = "Enter notes here..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                notesFiltering(newText)
                return true
            }
        })
    }

    private fun notesFiltering(newText: String?) {
        val newNoteList = arrayListOf<Notes>()
        for (i in oldNotesList) {
            if (i.title.contains(newText!!) || i.subtitle.contains(newText!!)) {
                newNoteList.add(i)
            }
        }
        adapter.filtering(newNoteList)
    }

    fun getALlNotes() {
        viewModel.getAllNotes().observe(viewLifecycleOwner) { notesList ->
            oldNotesList = notesList as ArrayList<Notes>
            adapter= NotesAdapter(requireContext(), notesList)
            binding.rvAllNotes.adapter =adapter
        }

    }
}