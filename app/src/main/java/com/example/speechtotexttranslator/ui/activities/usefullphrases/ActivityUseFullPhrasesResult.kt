package com.example.speechtotexttranslator.ui.activities.usefullphrases

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.adapters.AdapterUseFullPhrasesResult
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.USEFUL_PHRASES_LIST_NO
import com.example.speechtotexttranslator.databinding.ActivityUseFullPhrasesResultBinding
import com.example.speechtotexttranslator.db.usefullphrases.ViewModelUseFullPhrasesFavorites
import com.example.speechtotexttranslator.interfeces.TranslatorCallBack
import com.example.speechtotexttranslator.interfeces.TranslatorCallBack2
import com.example.speechtotexttranslator.models.ModelUseFullPhrasesResult
import com.example.speechtotexttranslator.utils.AppPreferences.funGetString
import com.example.speechtotexttranslator.utils.MLKit
import com.example.speechtotexttranslator.utils.Singleton.initTTS
import com.example.speechtotexttranslator.utils.Singleton.ttsShutdown
import com.google.mlkit.nl.translate.Translator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ActivityUseFullPhrasesResult : AppCompatActivity(), TranslatorCallBack, TranslatorCallBack2 {
    private var binding: ActivityUseFullPhrasesResultBinding? = null

    private lateinit var adapter: AdapterUseFullPhrasesResult
    var sourcePhrases: MutableList<ModelUseFullPhrasesResult> = ArrayList()
    private var targetPhrases: MutableList<String> = ArrayList()
    private val mlKit = MLKit(this)
    private lateinit var translator: Translator
    private lateinit var translator2: Translator
    var source = "en"
    var target = "en"
    var originalList: Array<String>? = null

    lateinit var scope: CoroutineScope
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityUseFullPhrasesResultBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding?.root)

        val int = intent.getIntExtra(USEFUL_PHRASES_LIST_NO, 0)
        val builder = AlertDialog.Builder(this)
            .setTitle("Data loading").setMessage("Wait a moment.Please").setCancelable(false)
        val alertDialog = builder.create()
        alertDialog.show()
        initTTS()


        scope = CoroutineScope(Dispatchers.IO)

        methLoadPhrases(int)

        scope.launch {
            delay(3000)
            launch(Dispatchers.Main)
            {
                if (alertDialog.isShowing) {
                    alertDialog.cancel()
                }
            }
        }
    }

    private fun methLoadPhrases(int: Int) {
        val viewMode = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelUseFullPhrasesFavorites::class.java)

        source = funGetString(
            AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES,
            "en"
        )
        target = funGetString(
            AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES,
            "en"
        )

        translator = mlKit.getTranslatorClient("en", source)
        translator2 = mlKit.getTranslatorClient(source, target)
        mlKit.funSetTranslatorCallBack(this)
        mlKit.funSetTranslatorCallBack2(this)
        val recyclerView = binding?.recyclerView!!

        adapter = AdapterUseFullPhrasesResult(this, viewMode)
        recyclerView.adapter = adapter
        val res = resources
        originalList = res.getStringArray(R.array.everyday_phrases_array)
        when (int) {
            0 -> {
                originalList = res.getStringArray(R.array.everyday_phrases_array)
            }
            1 -> {
                originalList = res.getStringArray(R.array.introduction_array)
            }
            2 -> {
                originalList = res.getStringArray(R.array.while_traveling_array)
            }
            3 -> {
                originalList = res.getStringArray(R.array.hotelling_and_accommodation_array)
            }
            4 -> {
                originalList = res.getStringArray(R.array.feelings_emotions_array)
            }
            5 -> {
                originalList = res.getStringArray(R.array.at_a_restaurant_array)
            }
            6 -> {
                originalList = res.getStringArray(R.array.at_the_supermarket_array)
            }
            7 -> {
                originalList = res.getStringArray(R.array.asking_directions_array)
            }
            8 -> {
                originalList = res.getStringArray(R.array.emergency_and_medical_help_array)
            }
            9 -> {
                originalList = res.getStringArray(R.array.days_months_num_array)
            }
            10 -> {
                originalList = res.getStringArray(R.array.problems_and_issues_array)
            }
        }
        val stringBuilder = StringBuilder()
        originalList!!.forEach { stringBuilder.append(it).append(" || ") }
        scope.launch {
            mlKit.translate(translator, stringBuilder.toString())
        }
        scope.launch { mlKit.translate2(translator2, stringBuilder.toString()) }
    }

    override fun call(result: String, source: String) {
        scope.launch(Dispatchers.Main) {
            val splitList = result.split(" ||")
            val sourceSplitList = source.split(" ||")
            for (i in 0 until splitList.size - 1) {
                sourcePhrases.add(ModelUseFullPhrasesResult(splitList[i], "",
                    sourceSplitList[i], target))
            }
        }
    }

    override fun failure(messages: String) {}

    override fun call2(result: String, source: String) {
        scope.launch {
            val splitList = result.split(" ||")
            for (i in 0 until splitList.size - 1) {
                targetPhrases.add(splitList[i])
            }
            launch(Dispatchers.Main) {
                adapter.submitList(sourcePhrases)
            }
            adapter.list = targetPhrases
        }
    }

    override fun failure2(messages: String) {
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsShutdown()
        binding = null
    }
}