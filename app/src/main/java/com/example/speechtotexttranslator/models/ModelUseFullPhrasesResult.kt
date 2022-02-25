package com.example.speechtotexttranslator.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

class ModelUseFullPhrasesResult(
    var source: String,
    var target: String,
    var sourceCode: String,
    var targetCode: String
)