package com.example.speechtotexttranslator.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.speechtotexttranslator.annotations.AnNot

@Entity(
    tableName = AnNot.ObjRoomItems.TABLE_FAVORITES_VOICE_TRANSLATOR,
    indices = [Index(
        value = ["_sourcesLanguage", "_targetLanguage", "_sourcesText", "_targetText","sourcesCode"
                ,"targetCode"],
        unique = true
    )]
)
class ModelVoiceTranslatorFavorites(
    sourcesCode: String,
    targetCode: String,
    sourcesLanguage: String,
    targetLanguage: String,
    sourcesText: String,
    targetText: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id = 0

    @ColumnInfo(name = "_sourcesLanguage")
    var sourcesLanguage: String? = sourcesLanguage

    @ColumnInfo(name = "_targetLanguage")
    var targetLanguage: String? = targetLanguage

    @ColumnInfo(name = "sourcesCode")
    var sourcesCode: String? = sourcesCode

    @ColumnInfo(name = "targetCode")
    var targetCode: String? = targetCode

    @ColumnInfo(name = "_sourcesText")
    var sourcesText: String? = sourcesText

    @ColumnInfo(name = "_targetText")
    var targetText: String? = targetText
}