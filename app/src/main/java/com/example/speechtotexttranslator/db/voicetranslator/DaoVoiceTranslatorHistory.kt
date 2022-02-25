package com.example.speechtotexttranslator.db.voicetranslator

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.speechtotexttranslator.models.ModelSpeakAndTranslateResult
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorFavorites
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorHistory

@Dao
interface DaoVoiceTranslatorHistory {
    @Insert
    fun insert(modelVoiceTranslatorHistory: ModelVoiceTranslatorHistory)

    @Update
    fun update(modelVoiceTranslatorHistory: ModelVoiceTranslatorHistory)

    @Delete
    fun delete(modelVoiceTranslatorHistory: ModelVoiceTranslatorHistory)

    @Query("DELETE FROM TABLE_HISTORY_VOICE_TRANSLATOR WHERE id = :id")
    fun deleteVoiceTranslatorHistory(id: Int)

    @Query("DELETE FROM TABLE_HISTORY_VOICE_TRANSLATOR")
    fun deleteAllVoiceTranslatorHistory()

    @Query("SELECT * FROM TABLE_HISTORY_VOICE_TRANSLATOR")
    fun getAllVoiceTranslatorHistory(): LiveData<List<ModelVoiceTranslatorHistory>>
}