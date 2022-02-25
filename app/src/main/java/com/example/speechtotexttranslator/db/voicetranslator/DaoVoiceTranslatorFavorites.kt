package com.example.speechtotexttranslator.db.voicetranslator

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.speechtotexttranslator.models.ModelSpeakAndTranslateResult
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorFavorites

@Dao
interface DaoVoiceTranslatorFavorites {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(modelVoiceTranslatorFavorites: ModelVoiceTranslatorFavorites)

    @Update
    fun update(modelVoiceTranslatorFavorites: ModelVoiceTranslatorFavorites)

    @Delete
    fun delete(modelVoiceTranslatorFavorites: ModelVoiceTranslatorFavorites)

    @Query("DELETE FROM TABLE_FAVORITES_VOICE_TRANSLATOR WHERE _id = :id")
    fun deleteVoiceTranslatorFavorite(id: Int)

    @Query("DELETE FROM TABLE_FAVORITES_VOICE_TRANSLATOR WHERE _sourcesText = :text1 AND _targetText = :text2")
    fun deleteVoiceTranslatorFavoriteByText(text1: String, text2: String)

    @Query("DELETE FROM TABLE_FAVORITES_VOICE_TRANSLATOR")
    fun deleteAllVoiceTranslatorFavorites()

    @Query("SELECT * FROM TABLE_FAVORITES_VOICE_TRANSLATOR")
    fun getAllVoiceTranslatorFavorites(): LiveData<List<ModelVoiceTranslatorFavorites>>

    @Query("SELECT * FROM TABLE_FAVORITES_VOICE_TRANSLATOR WHERE _sourcesText = :sourceText AND _targetText  =:resultText")
    fun isVoiceTranslatorFavoriteSExits(
        sourceText: String,
        resultText: String
    ): Int
}