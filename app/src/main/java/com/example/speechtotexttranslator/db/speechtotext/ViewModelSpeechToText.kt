package com.example.speechtotexttranslator.db.speechtotext

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelSpeechToTextNote

class ViewModelSpeechToText(application: Application) : AndroidViewModel(application) {

    private val repositorySpeechToText = RepositorySpeechToText(application)
    var results: LiveData<List<ModelSpeechToTextNote>> =
        repositorySpeechToText.results

    fun funInsert(result: ModelSpeechToTextNote) {
        repositorySpeechToText.funInsert(result);
    }

    fun funUpdate(result: ModelSpeechToTextNote) {
        repositorySpeechToText.funUpdate(result);
    }

    fun funDelete(result: ModelSpeechToTextNote) {
        repositorySpeechToText.funDelete(result)
    }

    fun funDelete(int: Int) {
        repositorySpeechToText.funDelete(int)
    }

    fun funDelete() {
        repositorySpeechToText.funDelete()
    }

    fun funGetAll(): LiveData<List<ModelSpeechToTextNote>> {
        return repositorySpeechToText.funGetAll()
    }
    fun funGet(id : Int): LiveData<ModelSpeechToTextNote> {
        return repositorySpeechToText.funGet(id)
    }

}