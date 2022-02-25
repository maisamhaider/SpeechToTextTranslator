package com.example.speechtotexttranslator.db.voicetranslator

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.db.MyRoom
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorFavorites
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorHistory
import com.example.speechtotexttranslator.utils.AppLevel

class RepositoryVoiceTranslatorHistory(application: Application) {

     var daoVoiceTranslatorHistory: DaoVoiceTranslatorHistory =
         (application as AppLevel).room.daoVoiceTranslatorHistory()!!

    var results: LiveData<List<ModelVoiceTranslatorHistory>> =
        daoVoiceTranslatorHistory.getAllVoiceTranslatorHistory()

    fun funGetAll(): LiveData<List<ModelVoiceTranslatorHistory>> {
        return daoVoiceTranslatorHistory.getAllVoiceTranslatorHistory()
    }

    fun funInsert(result: ModelVoiceTranslatorHistory) {
        Insert(result, daoVoiceTranslatorHistory)
    }

    fun funDelete(result: ModelVoiceTranslatorHistory) {
        Delete(result, daoVoiceTranslatorHistory)
    }

    fun funDelete(int: Int) {
        DeleteById(int, daoVoiceTranslatorHistory)
    }

    fun funDelete() {
        DeleteAll(daoVoiceTranslatorHistory)
    }

    internal class Insert(result: ModelVoiceTranslatorHistory, dao: DaoVoiceTranslatorHistory) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelVoiceTranslatorHistory
        private var dao: DaoVoiceTranslatorHistory

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

    internal class Delete(result: ModelVoiceTranslatorHistory, dao: DaoVoiceTranslatorHistory) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelVoiceTranslatorHistory
        private var dao: DaoVoiceTranslatorHistory

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

    internal class DeleteById(var int: Int, dao: DaoVoiceTranslatorHistory) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoVoiceTranslatorHistory

        override fun run() {
            dao.deleteVoiceTranslatorHistory(int)
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

    internal class DeleteAll(dao: DaoVoiceTranslatorHistory) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoVoiceTranslatorHistory

        override fun run() {
            dao.deleteAllVoiceTranslatorHistory()
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