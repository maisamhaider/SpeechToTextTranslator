package com.example.speechtotexttranslator.db.usefullphrases

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelUseFullPhrasesFavorites
import com.example.speechtotexttranslator.utils.AppLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors

class RepositoryUseFullPhasesFavorites(application: Application) {

    private var daoUseFullPhrasesFavorites: DaoUseFullPhrasesFavorites =
        (application as AppLevel).room.daoUseFullPhrases()!!

    var results: LiveData<List<ModelUseFullPhrasesFavorites>> =
        daoUseFullPhrasesFavorites.getAllUseFullPhrases()

    suspend fun isUseFullPhraseExists(sourceText: String, targetText: String) =
        withContext(Dispatchers.IO)
        {
            daoUseFullPhrasesFavorites.isUseFullPhraseExists(sourceText, targetText)
        }

    fun funGet(id: Int): LiveData<ModelUseFullPhrasesFavorites> {
        return daoUseFullPhrasesFavorites.getUseFullPhrases(id)
    }

    fun funGetAll(): LiveData<List<ModelUseFullPhrasesFavorites>> {
        return daoUseFullPhrasesFavorites.getAllUseFullPhrases()
    }

    fun funInsert(result: ModelUseFullPhrasesFavorites) {
        Insert(result, daoUseFullPhrasesFavorites)
    }

    fun funUpdate(result: ModelUseFullPhrasesFavorites) {
        Update(result, daoUseFullPhrasesFavorites)
    }

    fun funDelete(result: ModelUseFullPhrasesFavorites) {
        Delete(result, daoUseFullPhrasesFavorites)
    }

    fun funDelete(int: Int) {
        DeleteById(int, daoUseFullPhrasesFavorites)
    }

    fun funDelete(
        sourceText: String,
        targetText: String,
    ) {

        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            daoUseFullPhrasesFavorites.deleteUseFullPhrase(sourceText, targetText)
        }


    }

    fun funDelete() {
        DeleteAll(daoUseFullPhrasesFavorites)
    }


    internal class Insert(
        result: ModelUseFullPhrasesFavorites,
        daoFavorites: DaoUseFullPhrasesFavorites,
    ) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelUseFullPhrasesFavorites
        private var daoFavorites: DaoUseFullPhrasesFavorites

        override fun run() {
            daoFavorites.insert(result)
        }

        // for stopping the thread
        fun stop() {
            exit = true
        }

        init {
            exit = false
            this.result = result
            this.daoFavorites = daoFavorites
            t.start() // Starting the thread
        }
    }

    internal class Update(result: ModelUseFullPhrasesFavorites, dao: DaoUseFullPhrasesFavorites) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelUseFullPhrasesFavorites
        private var dao: DaoUseFullPhrasesFavorites

        override fun run() {
            dao.update(result)
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

    internal class Delete(result: ModelUseFullPhrasesFavorites, dao: DaoUseFullPhrasesFavorites) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelUseFullPhrasesFavorites
        private var dao: DaoUseFullPhrasesFavorites

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

    internal class DeleteById(var int: Int, dao: DaoUseFullPhrasesFavorites) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoUseFullPhrasesFavorites

        override fun run() {
            dao.deleteUseFullPhrase(int)
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

    internal class DeleteAll(dao: DaoUseFullPhrasesFavorites) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoUseFullPhrasesFavorites

        override fun run() {
            dao.deleteAllUseFullPhrases()
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