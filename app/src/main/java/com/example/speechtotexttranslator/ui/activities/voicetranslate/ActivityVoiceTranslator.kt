package com.example.speechtotexttranslator.ui.activities.voicetranslate

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.CODE_RESULT
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.CODE_SOURCE
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.IS_VOICE_TRANSLATOR_FAVORITE_RESULT
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.LANGUAGE_ONLINE
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.SOURCE_LANGUAGE
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.TARGET_LANGUAGE
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.TEXT_RESULT
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.TEXT_SOURCE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_NAME_VOICE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_CODE_VOICE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_VOICE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGE_SELECTED_VOICE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_NAME_VOICE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_CODE_VOICE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_VOICE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGE_SELECTED_VOICE_TRANSLATOR
import com.example.speechtotexttranslator.databinding.ActivityVoiceTranslatorBinding
import com.example.speechtotexttranslator.db.voicetranslator.ViewModelVoiceTranslatorHistory
import com.example.speechtotexttranslator.interfeces.TranslatorCallBack
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorHistory
import com.example.speechtotexttranslator.ui.activities.ActivityLanguages
import com.example.speechtotexttranslator.utils.AppPreferences.funGetString
import com.example.speechtotexttranslator.utils.Singleton.funLaunchLanguagesActivity
import com.example.speechtotexttranslator.utils.Singleton.funPaste
import com.example.speechtotexttranslator.utils.Singleton.isInternet
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import com.example.speechtotexttranslator.utils.apis.OnlineTranslatorApi

class ActivityVoiceTranslator : AppCompatActivity(), TranslatorCallBack {
    private var _binding: ActivityVoiceTranslatorBinding? = null
    private val binding get() = _binding!!

    private var viewModelVoiceTranslatorHistory: ViewModelVoiceTranslatorHistory? = null
    private var onlineTranslatorApi: OnlineTranslatorApi? = null

    private var source = "en"
    private var target = "en"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVoiceTranslatorBinding.inflate(LayoutInflater.from(this), null,
            false)
        setContentView(binding.root)
        onlineTranslatorApi = OnlineTranslatorApi(this)

        binding.apply {

            textViewSourceLanguage.setOnClickListener {
                funLaunchLanguagesActivity(
                    LANGUAGE_ONLINE,
                    SOURCE_RECENT_LANGUAGES_CODE_VOICE_TRANSLATOR,
                    SOURCE_RECENT_LANGUAGES_VOICE_TRANSLATOR,
                    SOURCE_RECENT_LANGUAGE_SELECTED_VOICE_TRANSLATOR,
                    SOURCE_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR,
                    SOURCE_LANGUAGE_SELECTED_NAME_VOICE_TRANSLATOR,
                    ActivityLanguages()
                )
            }
            textViewTargetLanguage.setOnClickListener {
                funLaunchLanguagesActivity(
                    LANGUAGE_ONLINE,
                    TARGET_RECENT_LANGUAGES_CODE_VOICE_TRANSLATOR,
                    TARGET_RECENT_LANGUAGES_VOICE_TRANSLATOR,
                    TARGET_RECENT_LANGUAGE_SELECTED_VOICE_TRANSLATOR,
                    TARGET_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR,
                    TARGET_LANGUAGE_SELECTED_NAME_VOICE_TRANSLATOR,
                    ActivityLanguages()
                )
            }
            textViewTranslate.setOnClickListener {
                source = funGetString(SOURCE_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR, "en")
                target = funGetString(TARGET_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR, "en")

                val input = editTextInput.text.toString()
                if (input.isNotEmpty()) {
                    if (isInternet()) {
                        onlineTranslatorApi!!.execute(input,
                            source,
                            target,
                            this@ActivityVoiceTranslator)

                    } else {
                        toastLong("No Internet connection")
                    }
                } else {
                    toastLong("No input found")
                }
            }
            imageViewMic.setOnClickListener {
                source = funGetString(SOURCE_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR, "en")
                target = funGetString(TARGET_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR, "en")

                if (isInternet()) {
                    displaySpeechRecognizer(source)
                } else {
                    toastLong("No Internet connection")
                }

            }
            imageViewPaste.setOnClickListener {
                val text: String = editTextInput.text.toString() + funPaste()
                editTextInput.setText(text)
            }
            textViewFavorites.setOnClickListener {
                startActivity(Intent(this@ActivityVoiceTranslator,
                    ActivityVoiceTranslatorFavoritesList::class.java))
            }
            textViewHistory.setOnClickListener {
                startActivity(Intent(this@ActivityVoiceTranslator,
                    ActivityVoiceTranslatorHistoriesList::class.java))
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
                onlineTranslatorApi!!.execute(spokenText, source, target, this)

            }
        }
    }


    override fun call(result: String, source: String) {
        val source1 = funGetString(SOURCE_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR, "en")
        target = funGetString(TARGET_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR, "en")
        if (result.isNotEmpty()) {
            val sourceLanguageSelectedName = funGetString(
                SOURCE_LANGUAGE_SELECTED_NAME_VOICE_TRANSLATOR, "English"
            )
            val targetLanguageSelectedName = funGetString(
                TARGET_LANGUAGE_SELECTED_NAME_VOICE_TRANSLATOR, "English"
            )
            val modelHistory = ModelVoiceTranslatorHistory(
                source1,
                target,
                sourceLanguageSelectedName,
                targetLanguageSelectedName,
                source,
                result
            )
            viewModelVoiceTranslatorHistory!!.funInsert(modelHistory)
            startActivity(Intent(this, ActivityVoiceTranslatorResult::class.java).apply {
                putExtra(CODE_SOURCE, source1)
                putExtra(CODE_RESULT, target)
                putExtra(TEXT_SOURCE, source)
                putExtra(TEXT_RESULT, result)
                putExtra(SOURCE_LANGUAGE, sourceLanguageSelectedName)
                putExtra(TARGET_LANGUAGE, targetLanguageSelectedName)
                putExtra(IS_VOICE_TRANSLATOR_FAVORITE_RESULT, false)
            })
        } else {
            toastLong(result)
        }

    }

    override fun failure(messages: String) {
        toastLong("Unknown error")
    }


    override fun onResume() {
        super.onResume()
        viewModelVoiceTranslatorHistory =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            ).get(ViewModelVoiceTranslatorHistory::class.java)

        binding.textViewSourceLanguage.text =
            funGetString(SOURCE_LANGUAGE_SELECTED_NAME_VOICE_TRANSLATOR, "English")
        binding.textViewTargetLanguage.text =
            funGetString(TARGET_LANGUAGE_SELECTED_NAME_VOICE_TRANSLATOR, "English")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}