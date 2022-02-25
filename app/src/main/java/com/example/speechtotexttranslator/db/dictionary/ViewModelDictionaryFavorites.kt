package com.example.speechtotexttranslator.db.dictionary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.db.voicetranslator.RepositoryVoiceTranslatorHistory
import com.example.speechtotexttranslator.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ViewModelDictionaryFavorites(application: Application) : AndroidViewModel(application) {

    private val repositoryDictionaryFavorites = RepositoryDictionaryFavorites(application)
    var results: LiveData<List<ModelDictionaryFavorites>> =
        repositoryDictionaryFavorites.results

    suspend fun isDictionaryResponse(word: String, response: String) =
        withContext(Dispatchers.IO)
        {
            repositoryDictionaryFavorites.isDictionaryResponse(word, response)
        }


    fun results(): LiveData<List<String>> {
        return repositoryDictionaryFavorites.results()
    }


    fun funInsert(result: ModelDictionaryFavorites) {
        repositoryDictionaryFavorites.funInsert(result);
    }

    fun funDelete(result: ModelDictionaryFavorites) {
        repositoryDictionaryFavorites.funDelete(result)
    }

    fun funDelete(int: Int) {
        repositoryDictionaryFavorites.funDelete(int)
    }
    fun funDelete(string: String) {
        repositoryDictionaryFavorites.funDelete(string)
    }

    fun funDelete() {
        repositoryDictionaryFavorites.funDelete()
    }

    fun funGetAll(): LiveData<List<ModelDictionaryFavorites>> {
        return repositoryDictionaryFavorites.funGetAll()
    }

}