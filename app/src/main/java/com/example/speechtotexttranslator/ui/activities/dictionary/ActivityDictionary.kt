package com.example.speechtotexttranslator.ui.activities.dictionary

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.databinding.ActivityDisctionaryBinding
import com.example.speechtotexttranslator.utils.Singleton.funTextToSpeech
import com.example.speechtotexttranslator.utils.Singleton.initTTS
import com.example.speechtotexttranslator.utils.Singleton.isInternet
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import com.example.speechtotexttranslator.utils.Singleton.ttsShutdown
import java.util.*


class ActivityDictionary : AppCompatActivity() {
    var _binding: ActivityDisctionaryBinding? = null
    val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDisctionaryBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding.root)

        binding.apply {

            searchEditText.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (searchEditText.text.toString().isNotBlank()) {
                        translate(searchEditText.text.toString())
                    } else {
                        toastLong("enter text first")
                    }
                }
                false
            }

            imageButtonMic.setOnClickListener {
                displaySpeechRecognizer("en")
            }
            textViewHistory.setOnClickListener {
                startActivity(Intent(this@ActivityDictionary,
                    ActivityDictionaryHistory::class.java))
            }
            textViewFavorites.setOnClickListener {
                startActivity(Intent(this@ActivityDictionary,
                    ActivityDictionaryFavorites::class.java))
            }
            textViewQuizOfTheDay.setOnClickListener {
                startActivity(Intent(this@ActivityDictionary, ActivityQuiz::class.java))
            }
        }
    }

    fun translate(query: String) {
        if (isInternet()) {
            if (query.isNotBlank() && query.isNotEmpty()) {
                startActivity(
                    Intent(
                        this@ActivityDictionary,
                        ActivityDictionaryResult::class.java
                    ).putExtra(AnNot.ObjIntentKeys.WORD, query.trim())
                )

            } else {
                toastLong("Enter a word please")
            }
        } else {
            toastLong("No active internet connection")
        }
    }

    private fun displaySpeechRecognizer(language: String) {
        val sRIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            ).putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
        }
        intentLauncher.launch(sRIntent)
    }

    private var intentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            if (result.data != null) {
                val spokenText: String =
                    result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        .let { results ->
                            results!![0]
                        }
                if (spokenText.contains(" ")) {
                    val f = spokenText.split(" ")
                    binding.searchEditText.setText(f[0])
                } else {
                    binding.searchEditText.setText(spokenText)
                }

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
        initTTS()
        val calendar = Calendar.getInstance()
        val dayOfTheYear = calendar.get(Calendar.DAY_OF_YEAR)

        val wordsList = resources.getStringArray(R.array.word_of_the_day_array)

        binding.textViewWordOfTheDay.text = wordsList[dayOfTheYear]

        binding.imageButtonSpeakWOTD.setOnClickListener {
            funTextToSpeech(wordsList[dayOfTheYear], "en")
        }
        binding.textViewWordOfTheDay.setOnClickListener {
            translate(wordsList[dayOfTheYear])
        }

    }


}