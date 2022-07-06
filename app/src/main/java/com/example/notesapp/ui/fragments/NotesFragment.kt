package com.example.notesapp.ui.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentNotesBinding
import com.example.notesapp.db.entities.Notes
import com.example.notesapp.viewmodels.NotesViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

class NotesFragment : Fragment() {

    val viewModel: NotesViewModel by viewModels()
    val notes by navArgs<NotesFragmentArgs>()
    lateinit var binding: FragmentNotesBinding
    private var priorityNote = "0"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotesBinding.inflate(layoutInflater, container, false)
        setData(notes.notesData)
        if (notes.notesData.id != null) {
            setHasOptionsMenu(true)
        } else {
            false
        }
        setOnAction()
        return binding.root
    }

    private fun setOnAction() {
        binding.saveNotesButton.setOnClickListener {
            if (getData().id != null) {
                viewModel.updateNote(getData())
                Toast.makeText(context, "Note update successfully", Toast.LENGTH_LONG).show()
            } else {
                viewModel.addNotes(getData())
                Toast.makeText(context, "Note add successfully", Toast.LENGTH_LONG).show()
            }
            Navigation.findNavController(it)
                .navigate(R.id.action_NotesFragment_to_homeFragment)
        }
        binding.redDot.setOnClickListener {
            binding.redDot.setImageResource(R.drawable.ic_check_white_24)
            binding.yellowDot.setImageResource(0)
            binding.greenDot.setImageResource(0)
            priorityNote = "1"
        }
        binding.yellowDot.setOnClickListener {
            binding.yellowDot.setImageResource(R.drawable.ic_check_white_24)
            binding.redDot.setImageResource(0)
            binding.greenDot.setImageResource(0)
            priorityNote = "2"
        }
        binding.greenDot.setOnClickListener {
            binding.greenDot.setImageResource(R.drawable.ic_check_white_24)
            binding.yellowDot.setImageResource(0)
            binding.redDot.setImageResource(0)
            priorityNote = "3"
        }
    }

    private fun setData(notes: Notes) {
        binding.noteTitle.setText(notes.title)
        binding.noteSubTitle.setText(notes.subtitle)
        binding.noteDescription.setText(notes.description)
        priorityNote = notes.priority
        when (priorityNote) {
            "1" -> {
                binding.redDot.setImageResource(R.drawable.ic_check_white_24)
            }
            "2" -> {
                binding.yellowDot.setImageResource(R.drawable.ic_check_white_24)
            }
            "3" -> {
                binding.greenDot.setImageResource(R.drawable.ic_check_white_24)
            }
        }
    }

    private fun getData(): Notes {
        val title = binding.noteTitle.text.toString()
        val subTitle = binding.noteSubTitle.text.toString()
        val description = binding.noteDescription.text.toString()
        val sdf = SimpleDateFormat("yyyy.MM.dd HH:mm")
        val currentDate = sdf.format(Date())
        val data = Notes(
            notes.notesData.id,
            title = title,
            subtitle = subTitle,
            description = description,
            createdDate = currentDate.toString(),
            priority = priorityNote
        )
        return data
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_delete) {
            val bottomSheetDialog: BottomSheetDialog =
                BottomSheetDialog(requireContext(), R.style.BottomSheetStyle)
            bottomSheetDialog.setContentView(R.layout.dialog_delete)
            bottomSheetDialog.show()

//            Toast.makeText(context, "Note delete successfully", Toast.LENGTH_LONG).show()
//            viewModel.deleteNotes(notes.notesData.id)

            val textViewYes = bottomSheetDialog.findViewById<TextView>(R.id.dialog_delete_yes)
            val textViewNo = bottomSheetDialog.findViewById<TextView>(R.id.dialog_delete_no)

            textViewYes?.setOnClickListener {
                viewModel.deleteNotes(notes.notesData.id!!)
                bottomSheetDialog.dismiss()
                Navigation.findNavController(this.view!!)
                    .navigate(R.id.action_NotesFragment_to_homeFragment)
            }

            textViewNo?.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}