package com.example.speechtotexttranslator.ui.activities.usefullphrases

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.speechtotexttranslator.adapters.AdapterUseFullPhrasesFavorites
import com.example.speechtotexttranslator.databinding.ActivityUseFullPhrasesFavoritesBinding
import com.example.speechtotexttranslator.db.usefullphrases.ViewModelUseFullPhrasesFavorites
import com.example.speechtotexttranslator.utils.Singleton.initTTS
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import com.example.speechtotexttranslator.utils.Singleton.ttsShutdown

class ActivityUseFullPhrasesFavorites : AppCompatActivity() {
    private var _binding: ActivityUseFullPhrasesFavoritesBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =
            ActivityUseFullPhrasesFavoritesBinding.inflate(LayoutInflater.from(this), null,
                false)
        setContentView(binding.root)
        initTTS()
    }

    private fun methLoadPhrases() {
        val viewMode = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelUseFullPhrasesFavorites::class.java)
        val adapter = AdapterUseFullPhrasesFavorites(this, viewMode)

        binding.apply {
            recyclerView.adapter = adapter
        }


        viewMode.funGetAll().observe(this@ActivityUseFullPhrasesFavorites) {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)
            } else {
                this@ActivityUseFullPhrasesFavorites.toastLong("No Favorites found")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsShutdown()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        methLoadPhrases()
    }
}