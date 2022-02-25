package com.example.speechtotexttranslator.utils.apis

import android.content.Context
import android.util.Log
import com.example.speechtotexttranslator.interfeces.DictionaryCallBack
import com.example.speechtotexttranslator.models.Definition
import com.example.speechtotexttranslator.models.DictionaryResponse
import com.example.speechtotexttranslator.models.Meaning
import com.example.speechtotexttranslator.models.Phonetic
import cz.msebera.android.httpclient.HttpResponse
import cz.msebera.android.httpclient.StatusLine
import cz.msebera.android.httpclient.client.HttpClient
import cz.msebera.android.httpclient.client.methods.HttpGet
import cz.msebera.android.httpclient.client.methods.HttpUriRequest
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.lang.StringBuilder
import java.net.URLEncoder
import org.json.JSONObject
import java.util.ArrayList


class DictionaryApi(val context: Context) {

    suspend fun execute(word: String, dictionaryCallBack: DictionaryCallBack): Unit =
        withContext(Dispatchers.IO)
        {
            try {
                val encoded: String = URLEncoder.encode(word, "utf-8")
                val stringBuilder = StringBuilder()

                stringBuilder.append("https://api.dictionaryapi.dev/api/v2/entries/en/")
                    .append(encoded)

                val client: HttpClient = HttpClientBuilder.create().build()
                val httpUriRequest: HttpUriRequest = HttpGet(stringBuilder.toString())
                val response: HttpResponse = client.execute(httpUriRequest)

                val statusLine: StatusLine = response.statusLine
                if (statusLine.statusCode == 200) {
                    val byteArrayOutputStream = ByteArrayOutputStream()

                    response.entity.writeTo(byteArrayOutputStream)

                    val byteArrayOutputStream2 = byteArrayOutputStream.toString()
                    byteArrayOutputStream.close()
//                    val obj = JsonObject(byteArrayOutputStream2)
                    var model: DictionaryResponse? = null
                    val listFinal: MutableList<DictionaryResponse> = ArrayList<DictionaryResponse>()
                    val jsonArray = JSONArray(byteArrayOutputStream2)
                    for (i in 0 until jsonArray.length()) {
                        val phoneticsFinal: ArrayList<Phonetic> = ArrayList<Phonetic>()
                        val meaningsFinal: MutableList<Meaning> = ArrayList<Meaning>()


                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        val word: String = jsonObject.getString("word")
                        Log.e("DictionaryApi", "word-------$word")
                        var phonetic = ""

                        if (jsonObject.has("phonetic")) {
                            phonetic = jsonObject.getString("phonetic")
                        }
                        Log.e("DictionaryApi", "phonetic-------$phonetic")


                        val phonetics: JSONArray = jsonObject.getJSONArray("phonetics")
                        for (j in 0 until phonetics.length()) {
                            val phoneticsObject: JSONObject = phonetics.getJSONObject(j)

                            var text = ""
                            if (phoneticsObject.has("text")) {
                                text = phoneticsObject.getString("text")
                            }
                            Log.e("DictionaryApi", "text-------$text")

                            var audio = ""
                            if (phoneticsObject.has("audio")) {
                                audio = phoneticsObject.getString("audio")
                            }
                            Log.e("DictionaryApi", "audio-------$audio")

                            phoneticsFinal.add(Phonetic(text, audio))
                        }

                        var origin = ""
                        if (jsonObject.has("origin")) {
                            origin = jsonObject.getString("origin")
                        }
                        Log.e("DictionaryApi", "origin-------$origin")

                        val meanings: JSONArray = jsonObject.getJSONArray("meanings")
                        for (k in 0 until meanings.length()) {
                            val meaningsObject: JSONObject = meanings.getJSONObject(k)
                            val partOfSpeech: String = meaningsObject.getString("partOfSpeech")
                            Log.e("DictionaryApi", "partOfSpeech-------$partOfSpeech")

                            val definitions: JSONArray = meaningsObject.getJSONArray("definitions")
                            val definitionsFinal: MutableList<Definition> = ArrayList<Definition>()

                            for (l in 0 until definitions.length()) {
                                val definitionsObject: JSONObject = definitions.getJSONObject(l)

                                val definition: String = definitionsObject.getString("definition")
                                Log.e("DictionaryApi", "definition-------$definition")
                                var example = ""
                                if (definitionsObject.has("example")) {
                                    example = definitionsObject.getString("example")
                                }
                                Log.e("DictionaryApi", "example-------$example")


                                val synonyms: JSONArray = definitionsObject.getJSONArray("synonyms")
                                Log.e("DictionaryApi", "synonyms-------$synonyms")
                                val antonyms: JSONArray = definitionsObject.getJSONArray("antonyms")
                                Log.e("DictionaryApi", "antonyms-------$antonyms")
                                val synonymsFinal: MutableList<String> = ArrayList<String>()

                                for (m in 0 until synonyms.length()) {
                                    synonymsFinal.add(synonyms[m].toString())

                                }

                                val antonymsFinal: MutableList<String> = ArrayList<String>()
                                for (n in 0 until antonyms.length()) {

                                    antonymsFinal.add(antonyms[n].toString())
                                }



                                definitionsFinal.add(
                                    Definition(
                                        definition,
                                        example, synonymsFinal,
                                        antonymsFinal
                                    )
                                )

                                meaningsFinal.add(Meaning(partOfSpeech, definitionsFinal))
                            }
                        }

                        model = DictionaryResponse(
                            word,
                            phonetic,
                            phoneticsFinal,
                            origin,
                            meaningsFinal
                        )
                        listFinal.add(model)
                    }
                    launch {
                        dictionaryCallBack.succeed(listFinal)
                        dictionaryCallBack.failed(byteArrayOutputStream2)

                    }

                } else {
                    launch {
                        dictionaryCallBack.failed("error")
                    }
                }

            } catch (er: Exception) {
                Log.e("DictionaryApi", er.message.toString())
            }


        }

//    fun execute(word: String, dictionaryCallBack: DictionaryCallBack) {
//        val executor: Executor = Executors.newSingleThreadExecutor()
//
//        executor.execute {
//
//            try {
//                val encoded: String = URLEncoder.encode(word, "utf-8")
//                val stringBuilder = StringBuilder()
//
//                stringBuilder.append("https://api.dictionaryapi.dev/api/v2/entries/en/")
//                    .append(encoded)
//
//                val client: HttpClient = HttpClientBuilder.create().build()
//                val httpUriRequest: HttpUriRequest = HttpGet(stringBuilder.toString())
//                val response: HttpResponse = client.execute(httpUriRequest)
//
//                val statusLine: StatusLine = response.statusLine
//                if (statusLine.statusCode == 200) {
////                    val jsonObject: JSONObject = JSONObject(response.entity.)
//                } else {
//                    dictionaryCallBack.failed("error")
//
//                }
//
//            } catch (er: Exception) {
//                Log.e("DictionaryApi", er.message.toString())
//            }
//
//
//        }
//    }
}