package com.example.speechtotexttranslator.db.speakandtranslate

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelSpeakAndTranslateResult

class ViewModelSpeakAndTranslateResult(application: Application) : AndroidViewModel(application) {

    private val repositoryTranslatedResultOnline = RepositorySpeakAndTranslateResult(application)
    var results: LiveData<List<ModelSpeakAndTranslateResult>> = repositoryTranslatedResultOnline.results

    fun funInsert(result: ModelSpeakAndTranslateResult) {
        repositoryTranslatedResultOnline.funInsert(result);
    }

    fun funDelete(result: ModelSpeakAndTranslateResult) {
        repositoryTranslatedResultOnline.funDelete(result)
    }

    fun funDelete(int: Int) {
        repositoryTranslatedResultOnline.funDelete(int)
    }

    fun funDelete() {
        repositoryTranslatedResultOnline.funDelete()
    }

    fun funGetAll(): LiveData<List<ModelSpeakAndTranslateResult>> {
        return repositoryTranslatedResultOnline.funGetAll()
    }

}