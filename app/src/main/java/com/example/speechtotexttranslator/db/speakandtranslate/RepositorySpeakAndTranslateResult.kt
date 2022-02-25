package com.example.speechtotexttranslator.db.speakandtranslate

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelSpeakAndTranslateResult
import com.example.speechtotexttranslator.utils.AppLevel

class RepositorySpeakAndTranslateResult(application: Application) {

    var daoSpeakAndTranslateResult: DaoSpeakAndTranslateResult =
        (application as AppLevel).room.daoTranslatedResultOnline()!!

    var results: LiveData<List<ModelSpeakAndTranslateResult>> =
        daoSpeakAndTranslateResult.getAllOnline()

    fun funGetAll(): LiveData<List<ModelSpeakAndTranslateResult>> {
        return daoSpeakAndTranslateResult.getAllOnline()
    }

    fun funInsert(result: ModelSpeakAndTranslateResult) {
        Insert(result, daoSpeakAndTranslateResult)
    }

    fun funDelete(result: ModelSpeakAndTranslateResult) {
        Delete(result, daoSpeakAndTranslateResult)
    }

    fun funDelete(int: Int) {
        DeleteById(int, daoSpeakAndTranslateResult)
    }

    fun funDelete() {
        DeleteAll(daoSpeakAndTranslateResult)
    }

    internal class Insert(result: ModelSpeakAndTranslateResult, dao: DaoSpeakAndTranslateResult) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelSpeakAndTranslateResult
        private var dao: DaoSpeakAndTranslateResult

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

    internal class Delete(result: ModelSpeakAndTranslateResult, dao: DaoSpeakAndTranslateResult) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelSpeakAndTranslateResult
        private var dao: DaoSpeakAndTranslateResult

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

    internal class DeleteById(var int: Int, dao: DaoSpeakAndTranslateResult) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoSpeakAndTranslateResult

        override fun run() {
            dao.deleteOnline(int)
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

    internal class DeleteAll(dao: DaoSpeakAndTranslateResult) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoSpeakAndTranslateResult

        override fun run() {
            dao.deleteAllOnline()
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