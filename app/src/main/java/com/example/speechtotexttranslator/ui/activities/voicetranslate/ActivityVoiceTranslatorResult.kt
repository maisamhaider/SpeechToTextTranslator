package com.example.speechtotexttranslator.ui.activities.voicetranslate

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.CODE_RESULT
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.CODE_SOURCE
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.IS_VOICE_TRANSLATOR_FAVORITE_RESULT
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.SOURCE_LANGUAGE
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.TARGET_LANGUAGE
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.TEXT_RESULT
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.TEXT_SOURCE
import com.example.speechtotexttranslator.databinding.ActivityVoiceTranslatorResultBinding
import com.example.speechtotexttranslator.db.voicetranslator.ViewModelVoiceTranslatorFavorites
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorFavorites
import com.example.speechtotexttranslator.utils.Singleton.funCopy
import com.example.speechtotexttranslator.utils.Singleton.funShare
import com.example.speechtotexttranslator.utils.Singleton.funTextToSpeech
import com.example.speechtotexttranslator.utils.Singleton.initTTS
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import com.example.speechtotexttranslator.utils.Singleton.ttsShutdown
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ActivityVoiceTranslatorResult : AppCompatActivity() {
    private var _binding: ActivityVoiceTranslatorResultBinding? = null
    private val binding get() = _binding!!

    private var sourceCode: String? = null
    private var targetCode: String? = null
    private var sourceText: String? = null
    private var resultText: String? = null
    private var sourceLanguage: String? = null
    private var resultLanguage: String? = null
    private var isFav: Boolean? = null
    private lateinit var viewModelVoiceTranslatorFavorites: ViewModelVoiceTranslatorFavorites
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityVoiceTranslatorResultBinding.inflate(LayoutInflater.from(this),
            null, false)
        setContentView(binding.root)
        initTTS()

        viewModelVoiceTranslatorFavorites =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            ).get(ViewModelVoiceTranslatorFavorites::class.java)

        sourceCode = intent.getStringExtra(CODE_SOURCE)
        targetCode = intent.getStringExtra(CODE_RESULT)

        sourceText = intent.getStringExtra(TEXT_SOURCE)
        resultText = intent.getStringExtra(TEXT_RESULT)
        sourceLanguage = intent.getStringExtra(SOURCE_LANGUAGE)
        resultLanguage = intent.getStringExtra(TARGET_LANGUAGE)
        isFav = intent.getBooleanExtra(IS_VOICE_TRANSLATOR_FAVORITE_RESULT, false)
        binding.apply {
            textViewSourceResult.text = sourceText
            textViewTargetResult.text = resultText

            textViewSourceLang.text = sourceLanguage
            textViewTargetLang.text = resultLanguage
            textViewFavorite.setOnClickListener {
                val model = ModelVoiceTranslatorFavorites(
                    sourceCode!!,
                    targetCode!!,
                    sourceLanguage!!,
                    resultLanguage!!,
                    sourceText!!,
                    resultText!!
                )
                val scope = CoroutineScope(Dispatchers.IO)
                scope.launch {
                    if (viewModelVoiceTranslatorFavorites.isVoiceTranslatorFavoriteSExits(
                            sourceText!!, resultText!!
                        ) > 0
                    ) {
                        viewModelVoiceTranslatorFavorites.funDelete(sourceText!!, resultText!!)
                        launch(Dispatchers.Main) {
                            toastLong("deletion successful}")
                            textViewFavorite.setBackgroundColor(Color.WHITE)
                        }
                    } else {
                        viewModelVoiceTranslatorFavorites.funInsert(model)
                        launch(Dispatchers.Main) {
                            toastLong("insertion successful")
                            textViewFavorite.setBackgroundColor(Color.GREEN)
                        }
                    }
                }
            }
            imageButtonSpeakSource.setOnClickListener {
                if (sourceText!!.isNotEmpty()) {
                    funTextToSpeech(
                        sourceText!!, sourceCode!!
                    )
                } else {
                    toastLong("no text found")
                }
            }
            imageButtonCopySource.setOnClickListener {
                funCopy(sourceText!!)
            }
            imageButtonShareSource.setOnClickListener {
                funShare(sourceText!!)
            }
            imageButtonSpeakTarget.setOnClickListener {
                if (resultText!!.isNotEmpty()) {
                    funTextToSpeech(resultText!!, targetCode!!)
                } else {
                    toastLong("no text found")
                }
            }
            imageButtonCopyTarget.setOnClickListener {
                funCopy(resultText!!)

            }
            imageButtonShareTarget.setOnClickListener {
                funShare(resultText!!)
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
        sourceText = intent.getStringExtra(TEXT_SOURCE)
        resultText = intent.getStringExtra(TEXT_RESULT)
        sourceLanguage = intent.getStringExtra(SOURCE_LANGUAGE)
        resultLanguage = intent.getStringExtra(TARGET_LANGUAGE)
        isFav = intent.getBooleanExtra(IS_VOICE_TRANSLATOR_FAVORITE_RESULT, false)

        CoroutineScope(Dispatchers.Main).launch {
            if (viewModelVoiceTranslatorFavorites.isVoiceTranslatorFavoriteSExits(
                    sourceText!!, resultText!!) > 0
            ) binding.textViewFavorite.setBackgroundColor(Color.GREEN)
            else binding.textViewFavorite.setBackgroundColor(Color.WHITE)

        }
    }

}