package com.example.notesapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.databinding.ItemNotesBinding
import com.example.notesapp.db.entities.Notes
import com.example.notesapp.ui.fragments.HomeFragmentDirections

class NotesAdapter(val requireContext: Context, var notesList: List<Notes>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val data = notesList[position]
        holder.binding.noteItemTitle.text = data.title
        holder.binding.noteItemDescription.text = data.description
        holder.binding.noteItemCreatedTime.text = data.createdDate
        when (data.priority) {
            "1" -> {
                holder.binding.priorityView.setBackgroundResource(R.drawable.red_ovel)
            }
            "2" -> {
                holder.binding.priorityView.setBackgroundResource(R.drawable.yellow_ovel)
            }
            "3" -> {
                holder.binding.priorityView.setBackgroundResource(R.drawable.green_ovel)
            }
        }
        holder.binding.root.setOnClickListener {
            /* val action = HomeFragmentDirections.actionHomeFragmentToEditNoteFragment(data)
             Navigation.findNavController(it).navigate(action)*/

            val action = HomeFragmentDirections.actionHomeFragmentToNotesFragment(data)
            Navigation.findNavController(it).navigate(action)
        }


    }

    override fun getItemCount() = notesList.size

    class NoteViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    fun filtering(newNoteList: ArrayList<Notes>) {
        notesList = newNoteList
        notifyDataSetChanged()
    }
}

