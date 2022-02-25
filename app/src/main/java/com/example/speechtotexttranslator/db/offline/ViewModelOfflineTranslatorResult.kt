package com.example.speechtotexttranslator.db.offline

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelOfflineTranslatorResult

class ViewModelOfflineTranslatorResult(application: Application) : AndroidViewModel(application) {

    private val repositoryTranslatedResult = RepositoryOfflineTranslatorResult(application)
    var results: LiveData<List<ModelOfflineTranslatorResult>> = repositoryTranslatedResult.results

    fun funInsert(result: ModelOfflineTranslatorResult) {
        repositoryTranslatedResult.funInsert(result);
    }

    fun funDelete(result: ModelOfflineTranslatorResult) {
        repositoryTranslatedResult.funDelete(result)
    }
    fun funDelete(int: Int) {
        repositoryTranslatedResult.funDelete(int)
    }

    fun funDelete() {
        repositoryTranslatedResult.funDelete()
    }

    fun funGetAll(): LiveData<List<ModelOfflineTranslatorResult>> {
        return repositoryTranslatedResult.funGetAll()
    }

}