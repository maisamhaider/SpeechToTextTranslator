package com.example.speechtotexttranslator.ui.activities

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.adapters.AdapterSpeakAndTranslateResult
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.LANGUAGE_ONLINE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_NAME_SPEAK_AND_TRANSLATE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_CODE_SPEAK_AND_TRANSLATE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_SPEAK_AND_TRANSLATE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGE_SELECTED_SPEAK_AND_TRANSLATE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_NAME_SPEAK_AND_TRANSLATE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_CODE_SPEAK_AND_TRANSLATE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_SPEAK_AND_TRANSLATE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGE_SELECTED_SPEAK_AND_TRANSLATE
import com.example.speechtotexttranslator.databinding.ActivitySpeechAndTranslateBinding
import com.example.speechtotexttranslator.db.speakandtranslate.ViewModelSpeakAndTranslateResult
import com.example.speechtotexttranslator.interfeces.TranslatorCallBack
import com.example.speechtotexttranslator.models.ModelSpeakAndTranslateResult
import com.example.speechtotexttranslator.utils.AppPreferences.funGetString
import com.example.speechtotexttranslator.utils.Singleton.funLaunchLanguagesActivity
import com.example.speechtotexttranslator.utils.Singleton.initTTS
import com.example.speechtotexttranslator.utils.Singleton.isInternet
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import com.example.speechtotexttranslator.utils.Singleton.ttsShutdown
import com.example.speechtotexttranslator.utils.apis.OnlineTranslatorApi

class ActivitySpeakAndTranslate : AppCompatActivity(), TranslatorCallBack {
    private var _binding: ActivitySpeechAndTranslateBinding? = null
    private val binding get() = _binding!!

    private lateinit var onlineTranslatorApi: OnlineTranslatorApi
    private lateinit var viewModelSpeakAndTranslateResult: ViewModelSpeakAndTranslateResult
    private lateinit var adapterSpeakAndTranslateResult: AdapterSpeakAndTranslateResult
    private var isSource = false
    private var source = "en"
    private var target = "en"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySpeechAndTranslateBinding.inflate(LayoutInflater.from(this),
            null, false)
        setContentView(binding.root)


    }

    private fun runCode() {
        initTTS()
        onlineTranslatorApi = OnlineTranslatorApi(this)

        viewModelSpeakAndTranslateResult = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelSpeakAndTranslateResult::class.java)

        runViewModelForLoadData()

        binding.apply {
            textViewSourceClick.setOnClickListener {
                if (isInternet()) {
                    source = funGetString(SOURCE_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE, "en")
                    target = funGetString(TARGET_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE, "en")
                    displaySpeechRecognizer(source)
                    isSource = true
                } else {
                    toastLong("No Internet connection")
                }

            }
            textViewTargetClick.setOnClickListener {
                if (isInternet()) {
                    //second button target is source in this case
                    source = funGetString(TARGET_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE, "en")
                    target = funGetString(
                        SOURCE_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE, "en"
                    )
                    displaySpeechRecognizer(source)
                    isSource = false
                } else {
                    toastLong("No Internet connection")
                }

            }

            textViewSourceLanguage.setOnClickListener {
                funLaunchLanguagesActivity(
                    LANGUAGE_ONLINE,
                    SOURCE_RECENT_LANGUAGES_CODE_SPEAK_AND_TRANSLATE,
                    SOURCE_RECENT_LANGUAGES_SPEAK_AND_TRANSLATE,
                    SOURCE_RECENT_LANGUAGE_SELECTED_SPEAK_AND_TRANSLATE,
                    SOURCE_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE,
                    SOURCE_LANGUAGE_SELECTED_NAME_SPEAK_AND_TRANSLATE,
                    ActivityLanguages()
                )
            }

            textViewTargetLanguage.setOnClickListener {
                funLaunchLanguagesActivity(
                    LANGUAGE_ONLINE,
                    TARGET_RECENT_LANGUAGES_CODE_SPEAK_AND_TRANSLATE,
                    TARGET_RECENT_LANGUAGES_SPEAK_AND_TRANSLATE,
                    TARGET_RECENT_LANGUAGE_SELECTED_SPEAK_AND_TRANSLATE,
                    TARGET_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE,
                    TARGET_LANGUAGE_SELECTED_NAME_SPEAK_AND_TRANSLATE,
                    ActivityLanguages()
                )
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
                onlineTranslatorApi.execute(spokenText, source, target, this)

            }
        }

    }

    private fun runViewModelForLoadData() {
        val recyclerViewTextToSpeech = findViewById<RecyclerView>(R.id.recyclerViewTextToSpeech)
        val llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterSpeakAndTranslateResult = AdapterSpeakAndTranslateResult(
            this,
            viewModelSpeakAndTranslateResult
        )
        recyclerViewTextToSpeech.layoutManager = llm
        recyclerViewTextToSpeech.adapter = adapterSpeakAndTranslateResult

        viewModelSpeakAndTranslateResult.results.observe(this) {
            if (it != null) {
                adapterSpeakAndTranslateResult.submitList(it)
            } else {
                toastLong("no data found")
            }
        }
    }


    override fun call(result: String, source: String) {
        if (isSource) {
            if (result != "") {
                viewModelSpeakAndTranslateResult.funInsert(
                    ModelSpeakAndTranslateResult(
                        funGetString(
                            SOURCE_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE,
                            "en"
                        ),
                        funGetString(
                            TARGET_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE,
                            "en"
                        ),
                        binding.textViewSourceLanguage.text.toString(),
                        binding.textViewTargetLanguage.text.toString(),
                        source,
                        result
                    )
                )
            }
        } else {
            if (result != "") {
                viewModelSpeakAndTranslateResult.funInsert(
                    ModelSpeakAndTranslateResult(
                        funGetString(
                            TARGET_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE,
                            "en"
                        ),
                        funGetString(
                            SOURCE_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE,
                            "en"
                        ),
                        binding.textViewTargetLanguage.text.toString(),
                        binding.textViewSourceLanguage.text.toString(),
                        source,
                        result
                    )
                )
            }
        }
        runViewModelForLoadData()
    }

    override fun failure(messages: String) {
        toastLong("Something went wrong")
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsShutdown()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        runCode()
        binding.textViewSourceLanguage.text = funGetString(
            SOURCE_LANGUAGE_SELECTED_NAME_SPEAK_AND_TRANSLATE,
            "English"
        )
        binding.textViewTargetLanguage.text = funGetString(
            TARGET_LANGUAGE_SELECTED_NAME_SPEAK_AND_TRANSLATE,
            "English"
        )
    }
}