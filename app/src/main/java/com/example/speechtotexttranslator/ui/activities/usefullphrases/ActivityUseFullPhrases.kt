package com.example.speechtotexttranslator.ui.activities.usefullphrases

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.adapters.AdapterUseFullPhrases
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.LANGUAGE_OFFLINE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_CODE_USEFUL_PHRASES
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_USEFUL_PHRASES
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGE_SELECTED_USEFUL_PHRASES
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_CODE_USEFUL_PHRASES
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_USEFUL_PHRASES
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGE_SELECTED_USEFUL_PHRASES
import com.example.speechtotexttranslator.databinding.ActivityUseFullPhrasesBinding
import com.example.speechtotexttranslator.interfeces.CallBackDownloadModel
import com.example.speechtotexttranslator.interfeces.TranslatorCallBack
import com.example.speechtotexttranslator.ui.activities.ActivityLanguages
import com.example.speechtotexttranslator.utils.AppPreferences.funGetString
import com.example.speechtotexttranslator.utils.AppPreferences.funGetStringSet
import com.example.speechtotexttranslator.utils.MLKit
import com.example.speechtotexttranslator.utils.Singleton.funLaunchLanguagesActivity
import com.example.speechtotexttranslator.utils.Singleton.funShowDownloadDialog
import com.example.speechtotexttranslator.utils.Singleton.isInternet
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import com.google.mlkit.nl.translate.Translator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class ActivityUseFullPhrases : AppCompatActivity(), CallBackDownloadModel, TranslatorCallBack {
    private var _binding: ActivityUseFullPhrasesBinding? =
        null
    private val binding get() = _binding!!


    private var translatedPhrasesList: MutableList<String> = ArrayList()
    private val mlKit = MLKit(this)
    private lateinit var translator: Translator
    private lateinit var adapter: AdapterUseFullPhrases
    private var source = "en"
    private var target = "en"
    private var modelSet: Set<String> = mutableSetOf()

    private lateinit var alertDialog: AlertDialog
    private lateinit var scope: CoroutineScope
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUseFullPhrasesBinding.inflate(LayoutInflater.from(this),
            null, false)
        setContentView(binding.root)
        scope = CoroutineScope(Dispatchers.IO)

        binding.apply {
            textViewSourceLang.setOnClickListener {
                funLaunchLanguagesActivity(
                    LANGUAGE_OFFLINE,
                    SOURCE_RECENT_LANGUAGES_CODE_USEFUL_PHRASES,
                    SOURCE_RECENT_LANGUAGES_USEFUL_PHRASES,
                    SOURCE_RECENT_LANGUAGE_SELECTED_USEFUL_PHRASES,
                    SOURCE_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES,
                    SOURCE_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES,
                    ActivityLanguages()
                )
            }
            textViewTargetLang.setOnClickListener {
                funLaunchLanguagesActivity(
                    LANGUAGE_OFFLINE,
                    TARGET_RECENT_LANGUAGES_CODE_USEFUL_PHRASES,
                    TARGET_RECENT_LANGUAGES_USEFUL_PHRASES,
                    TARGET_RECENT_LANGUAGE_SELECTED_USEFUL_PHRASES,
                    TARGET_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES,
                    TARGET_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES,
                    ActivityLanguages()
                )
            }
            textViewFavorites.setOnClickListener {
                startActivity(Intent(this@ActivityUseFullPhrases,
                    ActivityUseFullPhrasesFavorites::class.java))
            }
        }

    }

    private fun methLoadPhrases() {
        translator = mlKit.getTranslatorClient("en", source)
        mlKit.funSetTranslatorCallBack(this)

        adapter = AdapterUseFullPhrases(this)
        binding.recyclerView1.adapter = adapter

        val res = resources

        val list = res.getStringArray(R.array.useful_phrases_main)
        val stringBuilder = StringBuilder()

        scope.launch {
            list.forEach { stringBuilder.append(it).append(" //") }
            mlKit.translate(translator, stringBuilder.toString())
        }

    }

    private fun funDownloadDialogCall() {
        if (isInternet()) {
            funShowDownloadDialog(
                "Download Language", "This model may of size 30MB.\n" +
                        "Do you want to download?", "Yes", "No",
                this, this
            )
        } else {
            toastLong("Make sure you have an active internet connection")
        }
    }

    private fun progressDialog(
    ) {
        val builder = AlertDialog.Builder(this)
            .setTitle("Downloading...")
            .setMessage("Language is downloading...")
            .setCancelable(true)
        alertDialog = builder.create()
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()
        translatedPhrasesList.clear()
        modelSet = funGetStringSet(AnNot.ObjPreferencesKeys.DOWNLOADED_MODELS, setOf("en"))

        source = funGetString(
            SOURCE_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES,
            "en"
        )
        target = funGetString(
            TARGET_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES,
            "en"
        )

        if (modelSet.contains(source) && modelSet.contains(target)) {
            methLoadPhrases()
            val builder = AlertDialog.Builder(this)
                .setTitle("Data loading").setMessage("Wait a moment.Please").setCancelable(false)
            val alertDialog = builder.create()
            alertDialog.show()

            scope.launch {
                delay(2000)
                launch(Dispatchers.Main)
                {
                    if (alertDialog.isShowing) {
                        alertDialog.cancel()
                    }
                }
            }
            translator = mlKit.getTranslatorClient("en", source)
        } else {
            funDownloadDialogCall()
        }

        binding.textViewSourceLang.text = funGetString(
            SOURCE_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES, "English"
        )
        binding.textViewTargetLang.text = funGetString(
            TARGET_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES, "English"
        )
    }

    override fun buttonClicked(isSource: Boolean?) {
        if (!modelSet.contains(this.source) && !modelSet.contains(target)) {
            progressDialog()
            mlKit.downloadLanguageModel(mlKit.getTranslatorClient(this.source, target), this)
        } else if (!modelSet.contains(this.source)) {
            mlKit.downloadLanguageModel(mlKit.getTranslatorClient(this.source, target), this)
            progressDialog()
        } else if (!modelSet.contains(target)) {
            mlKit.downloadLanguageModel(mlKit.getTranslatorClient(this.source, target), this)
            progressDialog()
        }
    }


    override fun progress(process: String?) {
    }

    override fun completed(done: Boolean?) {
        if (alertDialog.isShowing) {
            alertDialog.cancel()
        }
        methLoadPhrases()
    }

    override fun call(result: String, source: String) {
        scope.launch {
            val splitList = result.split("//")
            for (i in 0 until splitList.size - 1) {
                translatedPhrasesList.add(splitList[i])
            }
            launch(Dispatchers.Main) {
                adapter.submitList(translatedPhrasesList)
            }
        }
    }

    override fun failure(messages: String) {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}