package com.example.speechtotexttranslator.interfeces


interface TranslatorCallBack {
    fun call(result: String, source: String)
    fun failure(messages: String)
}