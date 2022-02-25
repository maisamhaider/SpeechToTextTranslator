package com.example.speechtotexttranslator.models

import androidx.room.Entity

@Entity
data class Phonetic(var text: String, var audio: String)
