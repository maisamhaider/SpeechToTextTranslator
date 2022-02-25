package com.example.speechtotexttranslator.models

data class Definition(
    var definition: String,
    var example: String,
    var synonyms: List<String>,
    var antonyms: List<String>
)

