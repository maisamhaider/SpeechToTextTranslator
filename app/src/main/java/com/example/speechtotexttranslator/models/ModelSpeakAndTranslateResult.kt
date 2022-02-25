package com.example.speechtotexttranslator.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.speechtotexttranslator.annotations.AnNot.ObjRoomItems.TABLE_TRANSLATED_RESULTS_SPEAK_AND_TRANSLATE

@Entity(tableName = TABLE_TRANSLATED_RESULTS_SPEAK_AND_TRANSLATE)
class ModelSpeakAndTranslateResult(
    sourcesCode: String,
    targetCode: String,
    sourcesLanguage: String,
    targetLanguage: String,
    sourcesText: String,
    targetText: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id2")
    var id =  0

    @ColumnInfo(name = "_sourcesCode2")
    var sourcesCode: String? = sourcesCode

    @ColumnInfo(name = "_targetCode2")
    var targetCode: String? = targetCode

    @ColumnInfo(name = "_sourcesLanguage2")
    var sourcesLanguage: String? = sourcesLanguage

    @ColumnInfo(name = "_targetLanguage2")
    var targetLanguage: String? = targetLanguage

    @ColumnInfo(name = "_sourcesText2")
    var sourcesText: String? = sourcesText

    @ColumnInfo(name = "_targetText2")
    var targetText: String? = targetText


}