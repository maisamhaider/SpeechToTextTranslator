package com.example.speechtotexttranslator.db.offline

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelOfflineTranslatorResult
import com.example.speechtotexttranslator.utils.AppLevel

class RepositoryOfflineTranslatorResult(application: Application) {

    var daoTranslatedResult: DaoTranslatedResult =
        (application as AppLevel).room.daoTranslatedResult()!!

    var results: LiveData<List<ModelOfflineTranslatorResult>> = daoTranslatedResult.getAll()

    fun funGetAll(): LiveData<List<ModelOfflineTranslatorResult>> {
        return daoTranslatedResult.getAll()
    }

    fun funInsert(result: ModelOfflineTranslatorResult) {
        Insert(result, daoTranslatedResult)
    }

    fun funDelete(result: ModelOfflineTranslatorResult) {
        Delete(result, daoTranslatedResult)
    }

    fun funDelete(int: Int) {
        DeleteById(int, daoTranslatedResult)
    }

    fun funDelete() {
        DeleteAll(daoTranslatedResult)
    }

    internal class Insert(result: ModelOfflineTranslatorResult, dao: DaoTranslatedResult) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelOfflineTranslatorResult
        private var dao: DaoTranslatedResult

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

    internal class Delete(result: ModelOfflineTranslatorResult, dao: DaoTranslatedResult) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelOfflineTranslatorResult
        private var dao: DaoTranslatedResult

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

    internal class DeleteById(var int: Int, dao: DaoTranslatedResult) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoTranslatedResult

        override fun run() {
            dao.delete(int)
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

    internal class DeleteAll(dao: DaoTranslatedResult) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoTranslatedResult

        override fun run() {
            dao.deleteAll()
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