package com.example.speechtotexttranslator.db.dictionary

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelDictionaryHistory
import com.example.speechtotexttranslator.utils.AppLevel

class RepositoryDictionaryHistory(application: Application) {

    var daoDictionaryHistory: DaoDictionaryHistory =
        (application as AppLevel).room.daoDictionaryHistory()!!

    var results: LiveData<List<ModelDictionaryHistory>> =
        daoDictionaryHistory.getAllDictionaryHistories()

    fun results(): LiveData<List<String>> {
        return daoDictionaryHistory.getDictionaryHistories()

    }

    fun funGetAll(): LiveData<List<ModelDictionaryHistory>> {
        return daoDictionaryHistory.getAllDictionaryHistories()
    }

    fun funInsert(result: ModelDictionaryHistory) {
        Insert(result, daoDictionaryHistory)
    }

    fun funDelete(result: ModelDictionaryHistory) {
        Delete(result, daoDictionaryHistory)
    }

    fun funDelete(int: Int) {
        DeleteById(int, daoDictionaryHistory)
    }

    fun funDelete() {
        DeleteAll(daoDictionaryHistory)
    }

    internal class Insert(result: ModelDictionaryHistory, dao: DaoDictionaryHistory) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelDictionaryHistory
        private var dao: DaoDictionaryHistory

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

    internal class Delete(result: ModelDictionaryHistory, dao: DaoDictionaryHistory) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelDictionaryHistory
        private var dao: DaoDictionaryHistory

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

    internal class DeleteById(var int: Int, dao: DaoDictionaryHistory) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoDictionaryHistory

        override fun run() {
            dao.deleteDictionaryHistory(int)
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

    internal class DeleteAll(dao: DaoDictionaryHistory) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoDictionaryHistory

        override fun run() {
            dao.deleteDictionaryHistories()
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

