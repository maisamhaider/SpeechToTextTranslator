package com.example.speechtotexttranslator.db.usefullphrases

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelUseFullPhrasesFavorites
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors

class ViewModelUseFullPhrasesFavorites(application: Application) : AndroidViewModel(application) {

    private val repositoryUseFullPhases = RepositoryUseFullPhasesFavorites(application)
    var results: LiveData<List<ModelUseFullPhrasesFavorites>> = repositoryUseFullPhases.results

    fun funInsert(result: ModelUseFullPhrasesFavorites) {
        repositoryUseFullPhases.funInsert(result);
    }

    fun funUpdate(result: ModelUseFullPhrasesFavorites) {
        repositoryUseFullPhases.funUpdate(result);
    }

    fun funDelete(result: ModelUseFullPhrasesFavorites) {
        repositoryUseFullPhases.funDelete(result)
    }

    fun funDelete(int: Int) {
        repositoryUseFullPhases.funDelete(int)
    }

    fun funDelete() {
        repositoryUseFullPhases.funDelete()
    }

    fun funDelete(
        sourceText: String,
        targetText: String
    ) {
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            repositoryUseFullPhases.funDelete(sourceText, targetText)
        }

    }


    fun funGetAll(): LiveData<List<ModelUseFullPhrasesFavorites>> {
        return repositoryUseFullPhases.funGetAll()
    }

    fun funGet(id: Int): LiveData<ModelUseFullPhrasesFavorites> {
        return repositoryUseFullPhases.funGet(id)
    }

    suspend fun isUseFullPhraseExists(
        sourceText: String,
        targetText: String
    ) = repositoryUseFullPhases.isUseFullPhraseExists(sourceText, targetText)


}