package com.example.speechtotexttranslator.db.recentlanguages

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelRecentLanguages
import com.example.speechtotexttranslator.utils.AppLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryResultLanguages(application: Application) {

    var daoRecentLanguages: DaoRecentLanguages =
        (application as AppLevel).room.daoRecentLanguages()!!

    var results: LiveData<List<ModelRecentLanguages>> = daoRecentLanguages.getAllRL()

    suspend fun getFirstRow() = withContext(Dispatchers.IO) {
        daoRecentLanguages.getFirstRow()
    }

    fun funGetAll(): LiveData<List<ModelRecentLanguages>> {
        return daoRecentLanguages.getAllRL()
    }

    suspend fun isLangExists(code: String, name: String) = withContext(Dispatchers.IO) {
        daoRecentLanguages.isLangExists(code, name)
    }

    suspend fun entriesCount() = withContext(Dispatchers.IO) {
        daoRecentLanguages.entriesCount()
    }

    fun funInsert(result: ModelRecentLanguages) {
        Insert(result, daoRecentLanguages)
    }

    fun funDelete(result: ModelRecentLanguages) {
        Delete(result, daoRecentLanguages)
    }

    fun funDelete(int: Int) {
        DeleteById(int, daoRecentLanguages)
    }

    fun funDelete() {
        DeleteAll(daoRecentLanguages)
    }

    internal class Insert(result: ModelRecentLanguages, dao: DaoRecentLanguages) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelRecentLanguages
        private var dao: DaoRecentLanguages

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

    internal class Delete(result: ModelRecentLanguages, dao: DaoRecentLanguages) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelRecentLanguages
        private var dao: DaoRecentLanguages

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

    internal class DeleteById(var int: Int, dao: DaoRecentLanguages) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoRecentLanguages

        override fun run() {
            dao.deleteRL(int)
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

    internal class DeleteAll(dao: DaoRecentLanguages) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoRecentLanguages

        override fun run() {
            dao.deleteAllRL()
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