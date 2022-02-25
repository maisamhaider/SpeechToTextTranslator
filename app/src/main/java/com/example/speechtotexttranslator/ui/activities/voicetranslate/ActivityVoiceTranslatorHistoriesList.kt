package com.example.speechtotexttranslator.ui.activities.voicetranslate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.speechtotexttranslator.adapters.AdapterVoiceTranslatorHistory
import com.example.speechtotexttranslator.databinding.ActivityVoiceTranslatorHistoriesListBinding
import com.example.speechtotexttranslator.db.voicetranslator.ViewModelVoiceTranslatorHistory
import com.google.android.material.snackbar.Snackbar

class ActivityVoiceTranslatorHistoriesList : AppCompatActivity() {
    private var _binding: ActivityVoiceTranslatorHistoriesListBinding? = null
    private val binding get() = _binding!!
    private var viewModel: ViewModelVoiceTranslatorHistory? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVoiceTranslatorHistoriesListBinding.inflate(LayoutInflater.from(this),
            null, false)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelVoiceTranslatorHistory::class.java)

        val adapter = AdapterVoiceTranslatorHistory(this)
        binding.recyclerView.adapter = adapter

        viewModel!!.results.observe(this) {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
            }
        }

        binding.imageViewDelete.setOnClickListener {
            Snackbar.make(it, "Are you sure you want to delete history", Snackbar.LENGTH_LONG)
                .setAction("Delete") {
                    viewModel!!.funDelete()
                    binding.recyclerView.visibility = View.GONE
                }.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}