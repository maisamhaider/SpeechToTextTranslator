package com.example.speechtotexttranslator.db.offline

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.speechtotexttranslator.models.ModelOfflineTranslatorResult

@Dao
interface DaoTranslatedResult {
    @Insert
    fun insert(modelOfflineTranslatorResult: ModelOfflineTranslatorResult)

    @Update
    fun update(modelOfflineTranslatorResult: ModelOfflineTranslatorResult)

    @Delete
    fun delete(modelOfflineTranslatorResult: ModelOfflineTranslatorResult)

    @Query("DELETE FROM TRANSLATED_RESULTS_OFFLINE WHERE _id = :id")
    fun delete(id : Int)

    @Query("DELETE FROM TRANSLATED_RESULTS_OFFLINE")
    fun deleteAll()

    @Query("SELECT * FROM TRANSLATED_RESULTS_OFFLINE")
    fun getAll(): LiveData<List<ModelOfflineTranslatorResult>>
}