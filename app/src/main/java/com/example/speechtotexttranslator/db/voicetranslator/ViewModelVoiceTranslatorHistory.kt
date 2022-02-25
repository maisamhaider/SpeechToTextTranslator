package com.example.speechtotexttranslator.db.voicetranslator

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelSpeakAndTranslateResult
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorFavorites
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorHistory

class ViewModelVoiceTranslatorHistory(application: Application) : AndroidViewModel(application) {

    private val repositoryVoiceTranslatorHistory = RepositoryVoiceTranslatorHistory(application)
    var results: LiveData<List<ModelVoiceTranslatorHistory>> =
        repositoryVoiceTranslatorHistory.results

    fun funInsert(result: ModelVoiceTranslatorHistory) {
        repositoryVoiceTranslatorHistory.funInsert(result);
    }

    fun funDelete(result: ModelVoiceTranslatorHistory) {
        repositoryVoiceTranslatorHistory.funDelete(result)
    }

    fun funDelete(int: Int) {
        repositoryVoiceTranslatorHistory.funDelete(int)
    }

    fun funDelete() {
        repositoryVoiceTranslatorHistory.funDelete()
    }

    fun funGetAll(): LiveData<List<ModelVoiceTranslatorHistory>> {
        return repositoryVoiceTranslatorHistory.funGetAll()
    }

}