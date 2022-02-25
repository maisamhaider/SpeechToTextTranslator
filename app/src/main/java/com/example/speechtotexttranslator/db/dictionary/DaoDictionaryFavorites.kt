package com.example.speechtotexttranslator.db.dictionary

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.speechtotexttranslator.models.ModelDictionaryFavorites
import com.example.speechtotexttranslator.models.ModelDictionaryHistory
import com.example.speechtotexttranslator.models.ModelSpeakAndTranslateResult
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorFavorites

@Dao
interface DaoDictionaryFavorites {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(modelDictionaryFavorites: ModelDictionaryFavorites)

    @Update
    fun update(modelDictionaryFavorites: ModelDictionaryFavorites)

    @Delete
    fun delete(modelDictionaryFavorites: ModelDictionaryFavorites)

    @Query("DELETE FROM TABLE_DICTIONARY_FAVORITES WHERE id = :id")
    fun deleteDictionaryFavorites(id: Int)
    @Query("DELETE FROM TABLE_DICTIONARY_FAVORITES WHERE word = :word")
    fun deleteDictionaryFavorites(word: String)

    @Query("DELETE FROM TABLE_DICTIONARY_FAVORITES")
    fun deleteDictionaryFavorites()

    @Query("SELECT * FROM TABLE_DICTIONARY_FAVORITES")
    fun getAllDictionaryFavorites(): LiveData<List<ModelDictionaryFavorites>>

    @Query("SELECT word FROM TABLE_DICTIONARY_FAVORITES")
    fun getDictionaryFavorites(): LiveData<List<String>>

    @Query("SELECT * FROM TABLE_DICTIONARY_FAVORITES WHERE word = :word AND response = :response")
    fun isDictionaryExists(word: String, response: String): Int
}