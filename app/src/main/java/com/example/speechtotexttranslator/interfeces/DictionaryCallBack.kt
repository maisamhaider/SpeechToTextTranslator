package com.example.speechtotexttranslator.interfeces

import com.example.speechtotexttranslator.models.DictionaryResponse

interface DictionaryCallBack {

    fun succeed(dictionaryResponse: List<DictionaryResponse>)
    fun failed(error: String)
}