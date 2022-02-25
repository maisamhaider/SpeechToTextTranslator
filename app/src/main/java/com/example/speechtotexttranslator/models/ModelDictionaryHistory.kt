package com.example.speechtotexttranslator.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.annotations.AnNot.ObjRoomItems.TABLE_DICTIONARY_HISTORY

@Entity(
    tableName = TABLE_DICTIONARY_HISTORY, indices = [
        Index(value = ["word"], unique = true)
    ]
)
class ModelDictionaryHistory(
    word: String,
    response: String
) {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id = 0

    @ColumnInfo(name = "word")
    var word: String? = word

    @ColumnInfo(name = "response")
    var response: String? = response
}