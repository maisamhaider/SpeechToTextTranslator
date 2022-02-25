package com.example.speechtotexttranslator.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.appcompat.app.AppCompatActivity
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.DOWNLOADED_MODELS
import com.example.speechtotexttranslator.interfeces.CallBackDownloadModel
import com.example.speechtotexttranslator.interfeces.TranslatorCallBack
import com.example.speechtotexttranslator.interfeces.TranslatorCallBack2
import com.example.speechtotexttranslator.ui.activities.ActivityOfflineTranslator.Companion.supportedLanguages
import com.example.speechtotexttranslator.utils.AppPreferences.funAddStringSet
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MLKit(var context: Context?) {

    var translatorCallBack: TranslatorCallBack? = null
    var translatorCallBack2: TranslatorCallBack2? = null

    fun funSetTranslatorCallBack(translatorCallBack: TranslatorCallBack) {
        this.translatorCallBack = translatorCallBack
    }

    fun funSetTranslatorCallBack2(translatorCallBack2: TranslatorCallBack2) {
        this.translatorCallBack2 = translatorCallBack2
    }

    fun getLanguagesDetail() {
        val detailsIntent = Intent(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS)
        context!!.sendOrderedBroadcast(
            detailsIntent,
            null,
            LanguageDetailsChecker(),
            null,
            AppCompatActivity.RESULT_OK,
            null,
            null
        )
    }

    class LanguageDetailsChecker : BroadcastReceiver() {
        private var languages: List<String>? = null
        private var languagePreference: String? = null
        private var code: String? = null
        override fun onReceive(context: Context, intent: Intent?) {
            val results = getResultExtras(true)
            if (results.containsKey(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE)) {
                languagePreference = results.getString(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE)
            }
            if (results.containsKey(RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES)) {
                languages = results.getStringArrayList(
                    RecognizerIntent.EXTRA_SUPPORTED_LANGUAGES
                )
                if (languages != null) {
                    supportedLanguages.addAll(languages!!)
                    (languages as java.util.ArrayList<String>).forEach {
                        code = code + "\n" + it
                    }
                }

            }
        }
    }

    private fun setTranslatorLanguages(
        sourceLanguage: String,
        targetLanguage: String,
    ): TranslatorOptions {
        // setting source and target language
        //then returning TranslatorOptions.Builder. we will use this
        // to get Translator(Translator.getClient())
        return TranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build()

    }

    fun getTranslatorClient(
        sourceLanguage: String,
        targetLanguage: String,
    ): Translator {
        return Translation.getClient(setTranslatorLanguages(sourceLanguage, targetLanguage))
    }

    fun downloadLanguageModel(
        translation: Translator,
        callBackDownloadModel: CallBackDownloadModel,
    ) {
        val conditions = DownloadConditions.Builder()
            .build()
        translation.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                getDownloadedModels()
                callBackDownloadModel.completed(true)
            }
            .addOnFailureListener { _ ->
            }
    }

    fun translate(translator: Translator, text: String) {
        translator.translate(text)
            .addOnSuccessListener { translatedText ->
                if (translatedText.isNotEmpty()) {
                    translatorCallBack!!.call(translatedText, text)
                } else {
                    translatorCallBack!!.call("", text)
                }
                translator.close()
            }
            .addOnFailureListener { exception ->
                translatorCallBack!!.failure(exception.message.toString())
            }


    }

    fun translate2(translator: Translator, text: String) {
        translator.translate(text)
            .addOnSuccessListener { translatedText ->
                if (translatedText.isNotEmpty()) {
                    translatorCallBack2!!.call2(translatedText, text)
                } else {
                    translatorCallBack2!!.call2("", text)
                }
                translator.close()
            }
            .addOnFailureListener { exception ->
                translatorCallBack2!!.failure2(exception.message.toString())
            }


    }

    fun getDownloadedModels() {
        val modelManager = RemoteModelManager.getInstance()
        val set: MutableSet<String> = mutableSetOf()
        // Get translation models stored on the device.
        CoroutineScope(Dispatchers.IO).launch {
            modelManager.getDownloadedModels(TranslateRemoteModel::class.java)
                .addOnSuccessListener { models ->
                    models.forEach {
                        if (it != null) {
                            set.add(it.language)
                        }
                    }
                    context!!.funAddStringSet(DOWNLOADED_MODELS, set)

                }
                .addOnFailureListener {
                }
        }

    }

    fun checkDownloadedModels(language: String): Boolean {
        val modelManager = RemoteModelManager.getInstance()
        var result = false

        // Get translation models stored on the device.
        modelManager.getDownloadedModels(TranslateRemoteModel::class.java)
            .addOnSuccessListener { models ->
                models.forEach {
                    result = it.language == language
                }
            }
            .addOnFailureListener {
            }
        return result

    }

}


