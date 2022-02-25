package com.example.speechtotexttranslator.db.usefullphrases

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.example.speechtotexttranslator.models.ModelUseFullPhrasesFavorites

@Dao
interface DaoUseFullPhrasesFavorites {
    @Insert(onConflict = REPLACE)
    fun insert(modelUseFullPhrases: ModelUseFullPhrasesFavorites)

    @Query("SELECT * FROM TABLE_USE_FULL_PHRASES_FAVORITE")
    fun getAllUseFullPhrases(): LiveData<List<ModelUseFullPhrasesFavorites>>

    @Update
    fun update(modelUseFullPhrases: ModelUseFullPhrasesFavorites)

    @Delete
    fun delete(modelUseFullPhrases: ModelUseFullPhrasesFavorites)

    @Query("DELETE FROM TABLE_USE_FULL_PHRASES_FAVORITE WHERE id = :id")
    fun deleteUseFullPhrase(id: Int)

    @Query("DELETE FROM TABLE_USE_FULL_PHRASES_FAVORITE WHERE sourceText = :sourceText AND targetText = :targetText")
    fun deleteUseFullPhrase(sourceText: String, targetText: String)

    @Query("DELETE FROM TABLE_USE_FULL_PHRASES_FAVORITE")
    fun deleteAllUseFullPhrases()

    @Query("SELECT * FROM TABLE_USE_FULL_PHRASES_FAVORITE WHERE id = :id")
    fun getUseFullPhrases(id: Int): LiveData<ModelUseFullPhrasesFavorites>

    @Query("SELECT * FROM TABLE_USE_FULL_PHRASES_FAVORITE WHERE sourceText = :sourceText AND targetText = :targetText")
    fun isUseFullPhraseExists(sourceText: String, targetText: String): Int

}