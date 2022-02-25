package com.example.speechtotexttranslator.db.speechtotext

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.models.ModelSpeechToTextNote
import com.example.speechtotexttranslator.utils.AppLevel

class RepositorySpeechToText(application: Application) {

    var daoSpeechToText: DaoSpeechToText = (application as AppLevel).room.daoSpeechToText()!!

    var results: LiveData<List<ModelSpeechToTextNote>> = daoSpeechToText.getAllSpeechToTextNote()

    fun funGet(id: Int): LiveData<ModelSpeechToTextNote> {
        return daoSpeechToText.getSpeechToTextNote(id)
    }

    fun funGetAll(): LiveData<List<ModelSpeechToTextNote>> {
        return daoSpeechToText.getAllSpeechToTextNote()
    }

    fun funInsert(result: ModelSpeechToTextNote) {
        Insert(result, daoSpeechToText)
    }

    fun funUpdate(result: ModelSpeechToTextNote) {
        Update(result, daoSpeechToText)
    }

    fun funDelete(result: ModelSpeechToTextNote) {
        Delete(result, daoSpeechToText)
    }

    fun funDelete(int: Int) {
        DeleteById(int, daoSpeechToText)
    }

    fun funDelete() {
        DeleteAll(daoSpeechToText)
    }

    internal class Insert(result: ModelSpeechToTextNote, dao: DaoSpeechToText) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelSpeechToTextNote
        private var dao: DaoSpeechToText

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

    internal class Update(result: ModelSpeechToTextNote, dao: DaoSpeechToText) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelSpeechToTextNote
        private var dao: DaoSpeechToText

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

    internal class Delete(result: ModelSpeechToTextNote, dao: DaoSpeechToText) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelSpeechToTextNote
        private var dao: DaoSpeechToText

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

    internal class DeleteById(var int: Int, dao: DaoSpeechToText) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoSpeechToText

        override fun run() {
            dao.deleteSpeechToTextNote(int)
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

    internal class DeleteAll(dao: DaoSpeechToText) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoSpeechToText

        override fun run() {
            dao.deleteAllSpeechToTextNote()
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