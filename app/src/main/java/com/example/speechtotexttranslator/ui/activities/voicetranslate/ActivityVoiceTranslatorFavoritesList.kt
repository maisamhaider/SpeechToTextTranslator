package com.example.speechtotexttranslator.ui.activities.voicetranslate

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.speechtotexttranslator.adapters.AdapterVoiceTranslatorFavorite
import com.example.speechtotexttranslator.databinding.ActivityVoiceTranslatorFavoriratesListBinding
import com.example.speechtotexttranslator.db.voicetranslator.ViewModelVoiceTranslatorFavorites

class ActivityVoiceTranslatorFavoritesList : AppCompatActivity() {
    private var _binding: ActivityVoiceTranslatorFavoriratesListBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVoiceTranslatorFavoriratesListBinding.inflate(LayoutInflater.from(this),
            null, false)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        val viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(
            ViewModelVoiceTranslatorFavorites::class.java)

        val adapter = AdapterVoiceTranslatorFavorite(this)
        binding.recyclerView.adapter = adapter
        viewModel.results.observe(this) {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}