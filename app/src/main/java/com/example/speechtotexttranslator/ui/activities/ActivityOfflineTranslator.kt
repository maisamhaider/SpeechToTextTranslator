package com.example.speechtotexttranslator.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.adapters.AdapterOfflineTranslateResult
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.LANGUAGE_OFFLINE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.DOWNLOADED_MODELS
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_CODE_OFFLINE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_NAME_OFFLINE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_OFFLINE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_OFFLINE_TRANSLATOR_CODE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGE_SELECTED_OFFLINE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_OFFLINE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_NAME_OFFLINE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_OFFLINE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_OFFLINE_TRANSLATOR_CODE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGE_SELECTED_OFFLINE_TRANSLATOR
import com.example.speechtotexttranslator.databinding.ActivityOfflineTranslatorBinding
import com.example.speechtotexttranslator.db.offline.ViewModelOfflineTranslatorResult
import com.example.speechtotexttranslator.interfeces.CallBackDownloadModel
import com.example.speechtotexttranslator.interfeces.TranslatorCallBack
import com.example.speechtotexttranslator.models.ModelOfflineTranslatorResult
import com.example.speechtotexttranslator.utils.AppPreferences.funGetString
import com.example.speechtotexttranslator.utils.AppPreferences.funGetStringSet
import com.example.speechtotexttranslator.utils.Internet
import com.example.speechtotexttranslator.utils.MLKit
import com.example.speechtotexttranslator.utils.Singleton.funLaunchLanguagesActivity
import com.example.speechtotexttranslator.utils.Singleton.funShowDownloadDialog
import com.example.speechtotexttranslator.utils.Singleton.initTTS
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import com.example.speechtotexttranslator.utils.Singleton.toastShort
import com.example.speechtotexttranslator.utils.Singleton.ttsShutdown
import com.google.mlkit.nl.translate.Translator


class ActivityOfflineTranslator : AppCompatActivity(), TranslatorCallBack,
    CallBackDownloadModel {
    private var _binding: ActivityOfflineTranslatorBinding? = null
    private val binding get() = _binding!!

    private var internet: Internet? = null
    private var mlKit: MLKit? = null
    private var translator: Translator? = null
    private var viewModelOfflineTranslatorResult: ViewModelOfflineTranslatorResult? = null
    private var adapterOfflineTranslateResult: AdapterOfflineTranslateResult? = null
    private var source = "en"
    private var target = "en"
    private var modelSet: Set<String> = mutableSetOf()

    companion object {
        @JvmStatic
        var supportedLanguages = ArrayList<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityOfflineTranslatorBinding.inflate(LayoutInflater.from(this),
            null, false)
        setContentView(binding.root)
        runCode()
    }

    private fun runCode() {
        initTTS()
        internet = Internet(this)
        mlKit = MLKit(this)
        mlKit!!.funSetTranslatorCallBack(this)
        viewModelOfflineTranslatorResult = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelOfflineTranslatorResult::class.java)

        runViewModelForLoadData()
        binding.apply {


            btnTranslate.setOnClickListener {
                val editText = editTextInput.text.toString()
                if (editText.isNotEmpty()) {
                    modelSet = funGetStringSet(DOWNLOADED_MODELS, setOf("en"))
                    source = funGetString(SOURCE_LANGUAGE_SELECTED_CODE_OFFLINE_TRANSLATOR, "en")
                    target = funGetString(TARGET_LANGUAGE_SELECTED_CODE_OFFLINE_TRANSLATOR, "en")
                    if (modelSet.contains(source) && modelSet.contains(target)) {
                        translator = mlKit!!.getTranslatorClient(source, target)
                        mlKit!!.translate(translator!!, editText)
                    } else {
                        funDownloadDialogCall()
                    }
                } else {
                    toastShort("no input found")
                }
            }

            textViewSourceLanguage.setOnClickListener {
                funLaunchLanguagesActivity(
                    LANGUAGE_OFFLINE,
                    SOURCE_RECENT_LANGUAGES_OFFLINE_TRANSLATOR_CODE,
                    SOURCE_RECENT_LANGUAGES_OFFLINE_TRANSLATOR,
                    SOURCE_RECENT_LANGUAGE_SELECTED_OFFLINE_TRANSLATOR,
                    SOURCE_LANGUAGE_SELECTED_CODE_OFFLINE_TRANSLATOR,
                    SOURCE_LANGUAGE_SELECTED_NAME_OFFLINE_TRANSLATOR,
                    ActivityLanguages()
                )
            }

            textViewTargetLanguage.setOnClickListener {
                funLaunchLanguagesActivity(
                    LANGUAGE_OFFLINE,
                    TARGET_RECENT_LANGUAGES_OFFLINE_TRANSLATOR_CODE,
                    TARGET_RECENT_LANGUAGES_OFFLINE_TRANSLATOR,
                    TARGET_RECENT_LANGUAGE_SELECTED_OFFLINE_TRANSLATOR,
                    TARGET_LANGUAGE_SELECTED_CODE_OFFLINE_TRANSLATOR,
                    TARGET_LANGUAGE_SELECTED_NAME_OFFLINE_TRANSLATOR,
                    ActivityLanguages()
                )
            }
        }
    }

    private fun funDownloadDialogCall() {
        if (internet!!.isInternetAvailable(this)) {
            funShowDownloadDialog(
                "Download Language", "This model may of size 30MB.\n" +
                        "Do you want to download?", "Yes", "No",
                this, this
            )
        } else {
            toastLong("Make sure you have an active internet connection")
        }
    }


    private fun runViewModelForLoadData() {
        val recyclerViewTextToSpeech = findViewById<RecyclerView>(R.id.recyclerViewTextToSpeech)
        val llm = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapterOfflineTranslateResult = AdapterOfflineTranslateResult(
            this, viewModelOfflineTranslatorResult!!
        )
        recyclerViewTextToSpeech.layoutManager = llm
        recyclerViewTextToSpeech.adapter = adapterOfflineTranslateResult

        viewModelOfflineTranslatorResult!!.results.observe(this) {
            if (it != null) {
                adapterOfflineTranslateResult!!.submitList(it)
            } else {
                toastLong("no data found")
            }
        }
    }


    override fun call(result: String, source: String) {
        if (result != "") {
            viewModelOfflineTranslatorResult!!.funInsert(
                ModelOfflineTranslatorResult(
                    funGetString(SOURCE_LANGUAGE_SELECTED_CODE_OFFLINE_TRANSLATOR, "en"),
                    funGetString(TARGET_LANGUAGE_SELECTED_CODE_OFFLINE_TRANSLATOR, "en"),

                    binding.textViewSourceLanguage.text.toString(),
                    binding.textViewTargetLanguage.text.toString(),

                    source,
                    result
                )
            )
        }

        runViewModelForLoadData()
    }

    override fun failure(messages: String) {
        //
    }

    override fun buttonClicked(isSource: Boolean?) {
        if (!modelSet.contains(source) && !modelSet.contains(target)) {
            mlKit!!.downloadLanguageModel(mlKit!!.getTranslatorClient(source, target), this)
        } else if (!modelSet.contains(source)) {
            mlKit!!.downloadLanguageModel(mlKit!!.getTranslatorClient(source, target), this)
        } else if (!modelSet.contains(target)) {
            mlKit!!.downloadLanguageModel(mlKit!!.getTranslatorClient(source, target), this)
        }

    }

    override fun progress(process: String?) {
    }

    override fun completed(done: Boolean?) {
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsShutdown()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        binding.textViewSourceLanguage.text = funGetString(
            SOURCE_LANGUAGE_SELECTED_NAME_OFFLINE_TRANSLATOR,
            "English"
        )
        binding.textViewTargetLanguage.text = funGetString(
            TARGET_LANGUAGE_SELECTED_NAME_OFFLINE_TRANSLATOR,
            "English"
        )
    }
}