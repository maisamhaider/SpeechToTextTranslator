package com.example.speechtotexttranslator.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.annotations.AnNot.ObjRoomItems.TABLE_HISTORY_VOICE_TRANSLATOR

@Entity(tableName = TABLE_HISTORY_VOICE_TRANSLATOR)
class ModelVoiceTranslatorHistory(
    sourcesCode: String,
    targetCode: String,
    sourcesLanguage: String,
    targetLanguage: String,
    sourcesText: String,
    targetText: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id = 0

    @ColumnInfo(name = "_sourcesLanguage")
    var sourcesLanguage: String? = sourcesLanguage

    @ColumnInfo(name = "_targetLanguage")
    var targetLanguage: String? = targetLanguage

    @ColumnInfo(name = "_sourcesCode")
    var sourcesCode: String? = sourcesCode

    @ColumnInfo(name = "_targetCode")
    var targetCode: String? = targetCode

    @ColumnInfo(name = "_sourcesText")
    var sourcesText: String? = sourcesText

    @ColumnInfo(name = "_targetText")
    var targetText: String? = targetText
}