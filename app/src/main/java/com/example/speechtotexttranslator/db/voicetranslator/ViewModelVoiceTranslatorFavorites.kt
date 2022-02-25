package com.example.speechtotexttranslator.db.voicetranslator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorFavorites

class ViewModelVoiceTranslatorFavorites(application: Application) : AndroidViewModel(application) {

    private val repositoryVoiceTranslatorFavorites = RepositoryVoiceTranslatorFavorites(application)
    var results: LiveData<List<ModelVoiceTranslatorFavorites>> =
        repositoryVoiceTranslatorFavorites.results

    suspend fun isVoiceTranslatorFavoriteSExits(
        sourceText: String,
        resultText: String,
    ) = repositoryVoiceTranslatorFavorites.isVoiceTranslatorFavoriteSExits(
        sourceText, resultText
    )


    fun funInsert(result: ModelVoiceTranslatorFavorites) {
        repositoryVoiceTranslatorFavorites.funInsert(result);
    }

    fun funDelete(result: ModelVoiceTranslatorFavorites) {
        repositoryVoiceTranslatorFavorites.funDelete(result)
    }

    fun funDelete(int: Int) {
        repositoryVoiceTranslatorFavorites.funDelete(int)
    }

    fun funDelete(text1: String, text2: String) {
        repositoryVoiceTranslatorFavorites.funDelete(text1, text2)
    }

    fun funDelete() {
        repositoryVoiceTranslatorFavorites.funDelete()
    }

    fun funGetAll(): LiveData<List<ModelVoiceTranslatorFavorites>> {
        return repositoryVoiceTranslatorFavorites.funGetAll()
    }

}