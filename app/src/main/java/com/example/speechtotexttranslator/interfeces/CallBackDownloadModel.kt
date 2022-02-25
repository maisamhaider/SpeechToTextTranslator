package com.example.speechtotexttranslator.interfeces

interface CallBackDownloadModel {
    fun buttonClicked(isSource: Boolean?)
    fun progress(process: String?)
    fun completed(done: Boolean?)
}