package com.example.speechtotexttranslator.db.recentlanguages

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.db.offline.RepositoryOfflineTranslatorResult
import com.example.speechtotexttranslator.models.ModelOfflineTranslatorResult
import com.example.speechtotexttranslator.models.ModelRecentLanguages
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ViewModelResultLanguages(application: Application) : AndroidViewModel(application) {

    private val repositoryResultLanguages = RepositoryResultLanguages(application)
    var results: LiveData<List<ModelRecentLanguages>> = repositoryResultLanguages.results

    fun funInsert(result: ModelRecentLanguages) {
        repositoryResultLanguages.funInsert(result);
    }

    fun funDelete(result: ModelRecentLanguages) {
        repositoryResultLanguages.funDelete(result)
    }

    fun funDelete(int: Int) {
        repositoryResultLanguages.funDelete(int)
    }

    fun funDelete() {
        repositoryResultLanguages.funDelete()
    }

    suspend fun getFirstRow(): ModelRecentLanguages = withContext(Dispatchers.IO) {
        repositoryResultLanguages.getFirstRow()
    }

    fun funGetAll(): LiveData<List<ModelRecentLanguages>> {
        return repositoryResultLanguages.funGetAll()
    }

    suspend fun isLangExists(code: String, name: String) = withContext(Dispatchers.IO) {
        repositoryResultLanguages.isLangExists(code, name)
    }

    suspend fun entriesCount() = withContext(Dispatchers.IO) {
        repositoryResultLanguages.entriesCount()
    }

}