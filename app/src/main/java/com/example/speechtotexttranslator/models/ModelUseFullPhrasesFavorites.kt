package com.example.speechtotexttranslator.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.speechtotexttranslator.annotations.AnNot.ObjRoomItems.TABLE_USE_FULL_PHRASES_FAVORITE

@Entity(
    tableName = TABLE_USE_FULL_PHRASES_FAVORITE,
    indices = [Index(
        value = ["sourceText", "targetText"],
        unique = true
    )]
)
class ModelUseFullPhrasesFavorites(
    sourceCode: String,
    targetCode: String,
    sourceLang: String,
    targetLang: String,
    sourceText: String,
    targetText: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id = 0

    @ColumnInfo(name = "sourceLang")
    var sourceLang: String? = sourceLang

    @ColumnInfo(name = "targetLang")
    var targetLang: String? = targetLang

    @ColumnInfo(name = "sourceCode")
    var sourceCode: String? = sourceCode

    @ColumnInfo(name = "targetCode")
    var targetCode: String? = targetCode

    @ColumnInfo(name = "sourceText")
    var sourceText: String? = sourceText

    @ColumnInfo(name = "targetText")
    var targetText: String? = targetText
}