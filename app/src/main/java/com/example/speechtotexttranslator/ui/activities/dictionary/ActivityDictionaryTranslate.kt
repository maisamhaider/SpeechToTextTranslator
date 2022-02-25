package com.example.speechtotexttranslator.ui.activities.dictionary

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.TEXT
import com.example.speechtotexttranslator.databinding.ActivityDictionaryTranslatorBinding
import com.example.speechtotexttranslator.interfeces.TranslatorCallBack
import com.example.speechtotexttranslator.utils.AppPreferences.funGetString
import com.example.speechtotexttranslator.utils.Singleton.funCopy
import com.example.speechtotexttranslator.utils.Singleton.funShare
import com.example.speechtotexttranslator.utils.Singleton.isInternet
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import com.example.speechtotexttranslator.utils.apis.OnlineTranslatorApi

class ActivityDictionaryTranslate : AppCompatActivity(), TranslatorCallBack {
    private var _binding: ActivityDictionaryTranslatorBinding? = null
    private val binding get() = _binding!!
    private lateinit var onlineTranslatorApi: OnlineTranslatorApi
    private var target = "en"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding =
            ActivityDictionaryTranslatorBinding.inflate(LayoutInflater.from(this), null,
                false)
        setContentView(binding.root)
        onlineTranslatorApi = OnlineTranslatorApi(this)
        val text = intent.getStringExtra(TEXT)

        var target = funGetString(
            AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_DICTIONARY_RESULT,
            "en"
        )
        val name = funGetString(
            AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_NAME_DICTIONARY_RESULT,
            "English"
        )
        if (isInternet()) {
            onlineTranslatorApi.execute(text!!, "en", target, this)
        } else {
            toastLong("No Active internet connection")
        }


        binding.apply {
            editTextInput.setText(text)
            textViewTargetLang.text = name

            imageButtonCopySource.setOnClickListener {
                if (editTextInput.text.toString().isNotBlank()) {
                    funCopy(editTextInput.text.toString())
                } else {
                    toastLong("no text")

                }
            }
            imageButtonDeleteSource.setOnClickListener {
                editTextInput.setText("")
            }
            imageButtonSpeakTarget.setOnClickListener {
                if (textViewResult.text.isNotBlank()) {
                    toastLong(textViewResult.text.toString())

                } else {
                    toastLong("no text")

                }
            }
            imageButtonCopyTarget.setOnClickListener {
                if (textViewResult.text.isNotBlank()) {
                    funCopy(textViewResult.text.toString())
                } else {
                    toastLong("no text")

                }
            }
            imageButtonShare.setOnClickListener {
                if (textViewResult.text.isNotBlank()) {
                    funShare(textViewResult.text.toString())
                } else {
                    toastLong("no text")
                }
            }

            textViewTranslate.setOnClickListener {
                if (editTextInput.text.toString().isNotBlank()) {
                    funShare(textViewResult.text.toString())
                } else {
                    toastLong("no text")

                }
                if (isInternet()) {
                    target = funGetString(
                        AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE,
                        "en"
                    )
                    displaySpeechRecognizer("en")
                } else {
                    toastLong("No Internet connection")
                }
            }
        }


    }

    private fun displaySpeechRecognizer(language: String) {
//        sR = Speech Recognizer
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
                onlineTranslatorApi.execute(spokenText, "en", target, this)

            }
        }
    }

    override fun call(result: String, source: String) {
        if (result.isNotBlank()) {
            binding.textViewResult.text = result
        } else {
            toastLong("error")
        }
    }

    override fun failure(messages: String) {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}