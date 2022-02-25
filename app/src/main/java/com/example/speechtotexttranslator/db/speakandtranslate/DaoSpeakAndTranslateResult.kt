package com.example.speechtotexttranslator.db.speakandtranslate

import androidx.lifecycle.LiveData
import androidx.room.*
 import com.example.speechtotexttranslator.models.ModelSpeakAndTranslateResult

@Dao
interface DaoSpeakAndTranslateResult {
    @Insert
    fun insert(modelSpeakAndTranslateResult: ModelSpeakAndTranslateResult)

    @Update
    fun update(modelSpeakAndTranslateResult: ModelSpeakAndTranslateResult)

    @Delete
    fun delete(modelSpeakAndTranslateResult: ModelSpeakAndTranslateResult)

    @Query("DELETE FROM TABLE_TRANSLATED_RESULTS_SPEAK_AND_TRANSLATE WHERE _id2 = :idd")
    fun deleteOnline(idd : Int)

    @Query("DELETE FROM TABLE_TRANSLATED_RESULTS_SPEAK_AND_TRANSLATE")
    fun deleteAllOnline()

    @Query("SELECT * FROM TABLE_TRANSLATED_RESULTS_SPEAK_AND_TRANSLATE")
    fun getAllOnline(): LiveData<List<ModelSpeakAndTranslateResult>>
}