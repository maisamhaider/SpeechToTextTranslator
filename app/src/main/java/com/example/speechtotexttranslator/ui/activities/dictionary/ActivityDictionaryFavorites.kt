package com.example.speechtotexttranslator.ui.activities.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.speechtotexttranslator.adapters.AdapterDictionaryHistory
import com.example.speechtotexttranslator.databinding.ActivityDictionaryFavoritesBinding
import com.example.speechtotexttranslator.db.dictionary.ViewModelDictionaryFavorites
import com.example.speechtotexttranslator.utils.Singleton.toastLong

class ActivityDictionaryFavorites : AppCompatActivity() {
    private var _binding: ActivityDictionaryFavoritesBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =
            ActivityDictionaryFavoritesBinding.inflate(LayoutInflater.from(this), null,
                false)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelDictionaryFavorites::class.java)

        val adapter = AdapterDictionaryHistory(this@ActivityDictionaryFavorites)
        binding.recyclerView.adapter = adapter
        viewModel.results().observe(this) {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
            } else {
                toastLong("No favorites available")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}