package com.example.speechtotexttranslator.db.voicetranslator

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorFavorites
import com.example.speechtotexttranslator.utils.AppLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryVoiceTranslatorFavorites(application: Application) {

    private var daoVoiceTranslatorFavorites: DaoVoiceTranslatorFavorites =
        (application as AppLevel).room.daoVoiceTranslatorFavorites()!!

    var results: LiveData<List<ModelVoiceTranslatorFavorites>> =
        daoVoiceTranslatorFavorites.getAllVoiceTranslatorFavorites()

    suspend fun isVoiceTranslatorFavoriteSExits(
        sourceText: String,
        resultText: String,
    ) = withContext(Dispatchers.IO) {
        daoVoiceTranslatorFavorites.isVoiceTranslatorFavoriteSExits(sourceText, resultText)
    }

    fun funGetAll(): LiveData<List<ModelVoiceTranslatorFavorites>> {
        return daoVoiceTranslatorFavorites.getAllVoiceTranslatorFavorites()
    }

    fun funInsert(result: ModelVoiceTranslatorFavorites) {
        Insert(result, daoVoiceTranslatorFavorites)
    }

    fun funDelete(result: ModelVoiceTranslatorFavorites) {
        Delete(result, daoVoiceTranslatorFavorites)
    }

    fun funDelete(int: Int) {
        DeleteById(int, daoVoiceTranslatorFavorites)
    }

    fun funDelete(text1: String, text2: String) {
        DeleteByText(text1, text2, daoVoiceTranslatorFavorites)
    }

    fun funDelete() {
        DeleteAll(daoVoiceTranslatorFavorites)
    }

    internal class Insert(result: ModelVoiceTranslatorFavorites, dao: DaoVoiceTranslatorFavorites) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelVoiceTranslatorFavorites
        private var dao: DaoVoiceTranslatorFavorites

        override fun run() {
            dao.insert(result)
        }

        // for stopping the thread
        fun stop() {
            exit = true
        }

        init {
            exit = false
            this.result = result
            this.dao = dao
            t.start() // Starting the thread
        }
    }

    internal class Delete(result: ModelVoiceTranslatorFavorites, dao: DaoVoiceTranslatorFavorites) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelVoiceTranslatorFavorites
        private var dao: DaoVoiceTranslatorFavorites

        override fun run() {
            dao.delete(result)
        }

        // for stopping the thread
        fun stop() {
            exit = true
        }

        init {
            exit = false
            this.result = result
            this.dao = dao
            t.start() // Starting the thread
        }
    }

    internal class DeleteById(var int: Int, dao: DaoVoiceTranslatorFavorites) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoVoiceTranslatorFavorites

        override fun run() {
            dao.deleteVoiceTranslatorFavorite(int)
        }

        // for stopping the thread
        fun stop() {
            exit = true
        }

        init {
            exit = false
            this.dao = dao
            t.start() // Starting the thread
        }
    }

    internal class DeleteByText(
        var text1: String,
        var text2: String,
        dao: DaoVoiceTranslatorFavorites,
    ) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoVoiceTranslatorFavorites

        override fun run() {
            dao.deleteVoiceTranslatorFavoriteByText(text1, text2)
        }

        // for stopping the thread
        fun stop() {
            exit = true
        }

        init {
            exit = false
            this.dao = dao
            t.start() // Starting the thread
        }
    }

    internal class DeleteAll(dao: DaoVoiceTranslatorFavorites) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoVoiceTranslatorFavorites

        override fun run() {
            dao.deleteAllVoiceTranslatorFavorites()
        }

        // for stopping the thread
        fun stop() {
            exit = true
        }

        init {
            exit = false
            this.dao = dao
            t.start() // Starting the thread
        }
    }


}