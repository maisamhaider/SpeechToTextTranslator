package com.example.speechtotexttranslator.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.annotations.AnNot.ObjRoomItems.TABLE_HISTORY_VOICE_TRANSLATOR
import com.example.speechtotexttranslator.annotations.AnNot.ObjRoomItems.TABLE_NOTE_SPEECH_TO_TEXT

@Entity(tableName = TABLE_NOTE_SPEECH_TO_TEXT)
class ModelSpeechToTextNote(
    noteTitle: String,
    noteDate: String,
    noteLanguage: String,
    noteCode: String,
    noteText: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id = 0

    @ColumnInfo(name = "_noteTitle")
    var noteTitle: String? = noteTitle

    @ColumnInfo(name = "_noteDate")
    var noteDate: String? = noteDate

    @ColumnInfo(name = "_noteLanguage")
    var noteLanguage: String? = noteLanguage

    @ColumnInfo(name = "noteCode")
    var noteCode: String? = noteCode

    @ColumnInfo(name = "_noteText")
    var noteText: String? = noteText
}