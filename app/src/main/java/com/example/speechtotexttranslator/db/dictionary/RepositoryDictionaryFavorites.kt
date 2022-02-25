package com.example.speechtotexttranslator.db.dictionary

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.speechtotexttranslator.db.MyRoom
import com.example.speechtotexttranslator.models.ModelDictionaryFavorites
import com.example.speechtotexttranslator.utils.AppLevel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryDictionaryFavorites(application: Application) {

    private var room: MyRoom = MyRoom.getInstance(application)
    var daoDictionaryFavorites: DaoDictionaryFavorites =
        (application as AppLevel).room.daoDictionaryFavorites()!!

    var results: LiveData<List<ModelDictionaryFavorites>> =
        daoDictionaryFavorites.getAllDictionaryFavorites()

    suspend fun isDictionaryResponse(word: String, response: String): Int =
        withContext(Dispatchers.IO)
        {
            daoDictionaryFavorites.isDictionaryExists(word, response)
        }

    fun results(): LiveData<List<String>> {
        return daoDictionaryFavorites.getDictionaryFavorites()

    }

    fun funGetAll(): LiveData<List<ModelDictionaryFavorites>> {
        return daoDictionaryFavorites.getAllDictionaryFavorites()
    }

    fun funInsert(result: ModelDictionaryFavorites) {
        Insert(result, daoDictionaryFavorites)
    }

    fun funDelete(result: ModelDictionaryFavorites) {
        Delete(result, daoDictionaryFavorites)
    }

    fun funDelete(int: Int) {
        DeleteById(int, daoDictionaryFavorites)
    }

    fun funDelete(string: String) {
        DeleteByWord(string, daoDictionaryFavorites)
    }

    fun funDelete() {
        DeleteAll(daoDictionaryFavorites)
    }

    internal class Insert(result: ModelDictionaryFavorites, dao: DaoDictionaryFavorites) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelDictionaryFavorites
        private var dao: DaoDictionaryFavorites

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

    internal class Delete(result: ModelDictionaryFavorites, dao: DaoDictionaryFavorites) :
        Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var result: ModelDictionaryFavorites
        private var dao: DaoDictionaryFavorites

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

    internal class DeleteById(var int: Int, dao: DaoDictionaryFavorites) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoDictionaryFavorites

        override fun run() {
            dao.deleteDictionaryFavorites(int)
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

    internal class DeleteByWord(var word: String, dao: DaoDictionaryFavorites) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoDictionaryFavorites

        override fun run() {
            dao.deleteDictionaryFavorites(word)
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

    internal class DeleteAll(dao: DaoDictionaryFavorites) : Runnable {
        // to stop the thread
        private var exit: Boolean
        var t: Thread = Thread(this)
        private var dao: DaoDictionaryFavorites

        override fun run() {
            dao.deleteDictionaryFavorites()
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

