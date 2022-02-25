package com.example.speechtotexttranslator.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.speechtotexttranslator.annotations.AnNot.ObjRoomItems.TRANSLATED_RESULTS_OFFLINE

@Entity(tableName = TRANSLATED_RESULTS_OFFLINE)
class ModelOfflineTranslatorResult(
    sourcesCode: String,
    targetCode: String,
    sourcesLanguage: String,
    targetLanguage: String,
    sourcesText: String,
    targetText: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id =  0

    @ColumnInfo(name = "_sourcesCode")
    var sourcesCode: String? = sourcesCode

    @ColumnInfo(name = "_targetCode")
    var targetCode: String? = targetCode

    @ColumnInfo(name = "_sourcesLanguage")
    var sourcesLanguage: String? = sourcesLanguage

    @ColumnInfo(name = "_targetLanguage")
    var targetLanguage: String? = targetLanguage

    @ColumnInfo(name = "_sourcesText")
    var sourcesText: String? = sourcesText

    @ColumnInfo(name = "_targetText")
    var targetText: String? = targetText


}