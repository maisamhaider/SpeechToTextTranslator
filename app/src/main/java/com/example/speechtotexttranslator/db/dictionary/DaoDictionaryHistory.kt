package com.example.speechtotexttranslator.db.dictionary

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.speechtotexttranslator.models.ModelDictionaryHistory
import com.example.speechtotexttranslator.models.ModelSpeakAndTranslateResult
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorFavorites

@Dao
interface DaoDictionaryHistory {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(modelDictionaryHistory: ModelDictionaryHistory)

    @Update
    fun update(modelDictionaryHistory: ModelDictionaryHistory)

    @Delete
    fun delete(modelDictionaryHistory: ModelDictionaryHistory)

    @Query("DELETE FROM TABLE_DICTIONARY_HISTORY WHERE id = :id")
    fun deleteDictionaryHistory(id: Int)

    @Query("DELETE FROM TABLE_DICTIONARY_HISTORY")
    fun deleteDictionaryHistories()

    @Query("SELECT * FROM TABLE_DICTIONARY_HISTORY")
    fun getAllDictionaryHistories(): LiveData<List<ModelDictionaryHistory>>

    @Query("SELECT word FROM TABLE_DICTIONARY_HISTORY")
    fun getDictionaryHistories(): LiveData<List<String>>
}