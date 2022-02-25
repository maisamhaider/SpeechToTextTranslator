package com.example.speechtotexttranslator.ui.activities.dictionary

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.adapters.AdapterDictionaryResult
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.LANGUAGE_ONLINE
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.TEXT
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.WORD
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_DICTIONARY_RESULT
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_NAME_DICTIONARY_RESULT
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_CODE_DICTIONARY_RESULT
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_DICTIONARY_RESULT
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGE_SELECTED_DICTIONARY_RESULT
import com.example.speechtotexttranslator.databinding.ActivityDictionaryResultBinding
import com.example.speechtotexttranslator.db.dictionary.ViewModelDictionaryFavorites
import com.example.speechtotexttranslator.db.dictionary.ViewModelDictionaryHistory
import com.example.speechtotexttranslator.interfeces.DictionaryCallBack
import com.example.speechtotexttranslator.models.DictionaryResponse
import com.example.speechtotexttranslator.models.ModelDictionaryFavorites
import com.example.speechtotexttranslator.models.ModelDictionaryHistory
import com.example.speechtotexttranslator.ui.activities.ActivityLanguages
import com.example.speechtotexttranslator.utils.Singleton.funCopy
import com.example.speechtotexttranslator.utils.Singleton.funLaunchLanguagesActivity
import com.example.speechtotexttranslator.utils.Singleton.funShare
import com.example.speechtotexttranslator.utils.Singleton.funTextToSpeech
import com.example.speechtotexttranslator.utils.Singleton.initTTS
import com.example.speechtotexttranslator.utils.Singleton.ttsShutdown
import com.example.speechtotexttranslator.utils.apis.DictionaryApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class ActivityDictionaryResult : AppCompatActivity(), DictionaryCallBack {
    private var _binding: ActivityDictionaryResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ViewModelDictionaryHistory
    private lateinit var viewModelFavorites: ViewModelDictionaryFavorites
    var copied = ""
    var sb = StringBuilder()
    lateinit var word: String
    lateinit var alertDialog: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDictionaryResultBinding.inflate(LayoutInflater.from(this),
            null, false)
        setContentView(binding.root)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING)

        viewModelFavorites = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelDictionaryFavorites::class.java)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelDictionaryHistory::class.java)

        word = intent.getStringExtra(WORD).toString()

        val dictionaryApi = DictionaryApi(this)
        CoroutineScope(Dispatchers.IO).launch {
            dictionaryApi.execute(word, this@ActivityDictionaryResult)
        }
        val builder = AlertDialog.Builder(this)
            .setTitle("Translating").setMessage("Wait a moment.Please").setCancelable(false)
        alertDialog = builder.create()
        alertDialog.show()

        binding.apply {
            imageButtonSpeak.setOnClickListener {
                if (copied.isNotBlank()) {
                    funTextToSpeech(copied, "en")
                }
            }
            imageButtonFavorite.setOnClickListener {
                runBlocking(Dispatchers.IO) {
                    val liveData = viewModelFavorites.isDictionaryResponse(word, "")

                    if (liveData > 0) {
                        viewModelFavorites.funDelete(word)

                    } else {
                        val model = ModelDictionaryFavorites(word, "")
                        viewModelFavorites.funInsert(model)
                    }
                }
            }
            imageButtonCopy.setOnClickListener {
                funCopy(copied)
            }
            imageButtonShare.setOnClickListener {
                funShare(copied)
            }
            textViewTranslate.setOnClickListener {
                translateDialog()
            }
        }


    }


    private fun loadRecyclerView(dictionaryResponse: List<DictionaryResponse>) {
        val adapter = AdapterDictionaryResult(this)
        binding.recyclerView.adapter = adapter
        adapter.submitList(dictionaryResponse)
    }

    private fun translateDialog() {
        var word = true
        val view =
            LayoutInflater.from(this).inflate(
                R.layout.layout_dictionary_translate_dialog,
                null
            )

        val textViewLanguage: TextView = view.findViewById(R.id.textViewLanguage)
        val textViewTranslate: TextView = view.findViewById(R.id.textViewTranslate)
        val radioWord: RadioButton = view.findViewById(R.id.radioWord)
        val radioDescription: RadioButton = view.findViewById(R.id.radioDescription)

        val builder = AlertDialog.Builder(this).setView(view)

        val alertDialog: AlertDialog = builder.create()
        alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        textViewTranslate.setOnClickListener {
            startActivity(Intent(this, ActivityDictionaryTranslate::class.java).apply {
                if (word) {
                    putExtra(TEXT, this@ActivityDictionaryResult.word)
                } else {
                    putExtra(TEXT, sb.toString())
                }
            })

        }
        textViewLanguage.setOnClickListener {
            funLaunchLanguagesActivity(
                LANGUAGE_ONLINE,
                TARGET_RECENT_LANGUAGES_CODE_DICTIONARY_RESULT,
                TARGET_RECENT_LANGUAGES_DICTIONARY_RESULT,
                TARGET_RECENT_LANGUAGE_SELECTED_DICTIONARY_RESULT,
                TARGET_LANGUAGE_SELECTED_CODE_DICTIONARY_RESULT,
                TARGET_LANGUAGE_SELECTED_NAME_DICTIONARY_RESULT,
                ActivityLanguages()
            )
        }

        radioWord.setOnCheckedChangeListener { _, b ->
            if (b) {
                radioDescription.isChecked = false
                word = true
            }
        }
        radioDescription.setOnCheckedChangeListener { _, b ->
            if (b) {
                word = false
                radioWord.isChecked = false
            }
        }
    }

    override fun succeed(dictionaryResponse: List<DictionaryResponse>) {
        CoroutineScope(Dispatchers.Main).launch {
            loadRecyclerView(dictionaryResponse)
            if (!dictionaryResponse.isNullOrEmpty()) {
                val model = ModelDictionaryHistory(
                    dictionaryResponse[0].word,
                    ""
                )
                viewModel.funInsert(model)

                dictionaryResponse.forEach {
                    val word = it.word
                    sb.append("word: $word\n\n")
                    val phonetic = it.phonetic
                    if (phonetic.isNotBlank()) {
                        sb.append("phonetic: $phonetic\n\n")
                    }
                    val origin = it.origin
                    if (origin.isNotBlank()) {
                        sb.append("origin: $origin\n\n")
                    }
                    val meanings = it.meanings
                    meanings.forEach { it3 ->
                        if (it3.partOfSpeech.isNotBlank()) {
                            sb.append("part of speech: " + it3.partOfSpeech + "\n")
                        }
                        it3.definitions.forEach { it4 ->
                            if (it4.definition.isNotBlank()) {
                                sb.append("definition: " + it4.definition + "\n")
                            }
                            if (it4.example.isNotBlank()) {
                                sb.append("example: " + it4.example + "\n\n")
                            }
                            if (it4.synonyms.isNotEmpty()) {
                                sb.append("synonyms: " + it4.synonyms + "\n\n")
                            }
                            if (it4.antonyms.isNotEmpty()) {
                                sb.append("antonyms: " + it4.antonyms + "\n\n")
                            }
                        }
                    }
                }
                copied = sb.toString()
                if (alertDialog.isShowing) {
                    alertDialog.cancel()
                }

            }
            Log.e("ActivityDictionary", dictionaryResponse[0].origin)
        }
    }

    override fun failed(error: String) {
    }

    override fun onResume() {
        super.onResume()
        initTTS()
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsShutdown()
        _binding = null
    }

}