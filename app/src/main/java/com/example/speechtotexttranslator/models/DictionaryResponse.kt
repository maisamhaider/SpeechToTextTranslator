package com.example.speechtotexttranslator.models

import android.os.Parcelable
import com.google.android.material.internal.ParcelableSparseArray

data class DictionaryResponse(
    var word: String,
    var phonetic: String,
    var phonetics: List<Phonetic>,
    var origin: String,
    var meanings: List<Meaning>
)

