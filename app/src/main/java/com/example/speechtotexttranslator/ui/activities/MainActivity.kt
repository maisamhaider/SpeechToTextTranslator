package com.example.speechtotexttranslator.ui.activities

import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.speechtotexttranslator.databinding.ActivityMainBinding
import com.example.speechtotexttranslator.ui.activities.cameratranslator.ActivityCameraTranslator
import com.example.speechtotexttranslator.ui.activities.dictionary.ActivityDictionary
import com.example.speechtotexttranslator.ui.activities.speechtotext.ActivitySpeechToText
import com.example.speechtotexttranslator.ui.activities.usefullphrases.ActivityUseFullPhrases
import com.example.speechtotexttranslator.ui.activities.voicetranslate.ActivityVoiceTranslator
import com.example.speechtotexttranslator.utils.MLKit
import com.example.speechtotexttranslator.utils.Singleton.initQuizOfTheDay
import com.example.speechtotexttranslator.utils.Singleton.isInternet
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(LayoutInflater.from(this),
            null, false)
        setContentView(binding.root)
        Arrays.toString(Locale.getAvailableLocales())

        binding.apply {
            textViewTextToSpeechOffline.setOnClickListener {
                startActivity(Intent(this@MainActivity, ActivityOfflineTranslator::class.java))
            }
            textViewSpeakAndTranslate.setOnClickListener {
                startActivity(Intent(this@MainActivity, ActivitySpeakAndTranslate::class.java))
            }
            textViewVoiceTranslate.setOnClickListener {
                startActivity(Intent(this@MainActivity, ActivityVoiceTranslator::class.java))
            }
            textViewUsefulPhrases.setOnClickListener {
                if (isInternet()) {
                    startActivity(Intent(this@MainActivity, ActivityUseFullPhrases::class.java))
                } else {
                    toastLong("No active internet")
                }
            }
            textViewSpeechToText.setOnClickListener {
                startActivity(Intent(this@MainActivity, ActivitySpeechToText::class.java))
            }
            textViewEnglishDictionary.setOnClickListener {
                startActivity(Intent(this@MainActivity, ActivityDictionary::class.java))
            }
            textViewCameraTranslator.setOnClickListener {
                startActivity(Intent(this@MainActivity, ActivityCameraTranslator::class.java))
            }
            textViewKeyboard.setOnClickListener {
                startActivity(Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS))
            }
        }

        val mlKit = MLKit(this)
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            mlKit.getDownloadedModels()
            initQuizOfTheDay()
        }
    }

    override fun onInit(p0: Int) {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}