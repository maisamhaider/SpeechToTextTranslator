package com.example.speechtotexttranslator.db.dictionary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.db.voicetranslator.RepositoryVoiceTranslatorHistory
import com.example.speechtotexttranslator.models.ModelDictionaryHistory
import com.example.speechtotexttranslator.models.ModelSpeakAndTranslateResult
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorFavorites
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorHistory

class ViewModelDictionaryHistory(application: Application) : AndroidViewModel(application) {

    private val repositoryDictionaryHistory = RepositoryDictionaryHistory(application)
    var results: LiveData<List<ModelDictionaryHistory>> =
        repositoryDictionaryHistory.results

    fun results(): LiveData<List<String>> {
        return repositoryDictionaryHistory.results()
    }


    fun funInsert(result: ModelDictionaryHistory) {
        repositoryDictionaryHistory.funInsert(result);
    }

    fun funDelete(result: ModelDictionaryHistory) {
        repositoryDictionaryHistory.funDelete(result)
    }

    fun funDelete(int: Int) {
        repositoryDictionaryHistory.funDelete(int)
    }

    fun funDelete() {
        repositoryDictionaryHistory.funDelete()
    }

    fun funGetAll(): LiveData<List<ModelDictionaryHistory>> {
        return repositoryDictionaryHistory.funGetAll()
    }

}