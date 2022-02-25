package com.example.speechtotexttranslator.interfeces


interface TranslatorCallBack2 {
    fun call2(result: String, source: String)
    fun failure2(messages: String)
}