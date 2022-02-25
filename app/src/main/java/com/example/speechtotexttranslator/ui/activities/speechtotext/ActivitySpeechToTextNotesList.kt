package com.example.speechtotexttranslator.ui.activities.speechtotext

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.adapters.AdapterSpeechToTextNote
import com.example.speechtotexttranslator.databinding.ActivitySpeechToTextNotesListBinding
import com.example.speechtotexttranslator.db.speechtotext.ViewModelSpeechToText
import com.example.speechtotexttranslator.utils.Singleton.toastLong

class ActivitySpeechToTextNotesList : AppCompatActivity() {
    private var _binding: ActivitySpeechToTextNotesListBinding? = null
    private val binding get() = _binding!!

    private var viewModel: ViewModelSpeechToText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySpeechToTextNotesListBinding.inflate(LayoutInflater.from(this), null,
            false)
        setContentView(binding.root)
        val imageButtonDelete = findViewById<ImageButton>(R.id.imageButtonDelete)
        imageButtonDelete.setOnClickListener {
            viewModel!!.funDelete()
            loadRecycler()
        }


    }

    private fun loadRecycler() {

        val adapter = AdapterSpeechToTextNote(this)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelSpeechToText::class.java)

        viewModel!!.funGetAll().observe(this) {
            if (it != null) {
                adapter.submitList(it)
            } else {
                toastLong("No saved notes found")
            }
        }
        binding.recyclerView.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        loadRecycler()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}