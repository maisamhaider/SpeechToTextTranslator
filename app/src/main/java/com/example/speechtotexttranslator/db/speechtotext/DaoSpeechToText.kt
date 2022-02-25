package com.example.speechtotexttranslator.db.speechtotext

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.speechtotexttranslator.models.ModelSpeakAndTranslateResult
import com.example.speechtotexttranslator.models.ModelSpeechToTextNote

@Dao
interface DaoSpeechToText {
    @Insert
    fun insert(modelSpeechToTextNote: ModelSpeechToTextNote)

    @Update
    fun update(modelSpeechToTextNote: ModelSpeechToTextNote)

    @Delete
    fun delete(modelSpeechToTextNote: ModelSpeechToTextNote)

    @Query("DELETE FROM TABLE_NOTE_SPEECH_TO_TEXT WHERE id = :id")
    fun deleteSpeechToTextNote(id: Int)

    @Query("DELETE FROM TABLE_NOTE_SPEECH_TO_TEXT")
    fun deleteAllSpeechToTextNote()

    @Query("SELECT * FROM TABLE_NOTE_SPEECH_TO_TEXT WHERE id = :id")
    fun getSpeechToTextNote(id: Int): LiveData<ModelSpeechToTextNote>

    @Query("SELECT * FROM TABLE_NOTE_SPEECH_TO_TEXT")
    fun getAllSpeechToTextNote(): LiveData<List<ModelSpeechToTextNote>>
}