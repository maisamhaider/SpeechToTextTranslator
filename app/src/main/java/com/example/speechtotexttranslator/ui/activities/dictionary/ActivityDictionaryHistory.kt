package com.example.speechtotexttranslator.ui.activities.dictionary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.speechtotexttranslator.adapters.AdapterDictionaryHistory
import com.example.speechtotexttranslator.databinding.ActivityDictionaryHistoryBinding
import com.example.speechtotexttranslator.db.dictionary.ViewModelDictionaryHistory
import com.example.speechtotexttranslator.models.Definition
import com.example.speechtotexttranslator.models.DictionaryResponse
import com.example.speechtotexttranslator.models.Meaning
import com.example.speechtotexttranslator.models.Phonetic
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import org.json.JSONArray
import org.json.JSONObject

class ActivityDictionaryHistory : AppCompatActivity() {
    private var _binding: ActivityDictionaryHistoryBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDictionaryHistoryBinding.inflate(LayoutInflater.from(this),
            null, false)
        setContentView(binding.root)
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelDictionaryHistory::class.java)

        val adapter = AdapterDictionaryHistory(this@ActivityDictionaryHistory)
        binding.apply {
            recyclerView.adapter = adapter
            imageViewDelete.setOnClickListener {
                viewModel.funDelete()
                recyclerView.visibility = View.GONE
            }
        }
        viewModel.results().observe(this) {
            if (!it.isNullOrEmpty()) {
                adapter.submitList(it)

            } else {
                toastLong("No history available")
            }
        }

    }

    private fun getStringBuilder(res: List<DictionaryResponse>): StringBuilder {
        val sb = StringBuilder()

        if (!res.isNullOrEmpty()) {

            res.forEach {
                val word = it.word
                sb.append("word: $word\n\n")
                val phonetic = it.phonetic
                if (phonetic.isNotBlank()) {
                    sb.append("phonetic: $phonetic\n\n")
                }
                val phonetics = it.phonetics
                sb.append("phonetics: " + "\n\n")
                phonetics.forEach { it2 ->
                    if (it2.text.isNotBlank()) {
                        sb.append("text: " + it2.text + "\n")
                    }
//                        if (it2.audio.isNotBlank()) {
//                            sb.append("audio: " + it2.audio + "\n\n")
//                        }
                }
                val origin = it.origin
                if (origin.isNotBlank()) {
                    sb.append("origin: $origin\n\n")
                }
                val meanings = it.meanings
                sb.append("meanings: \n\n")
                meanings.forEach { it3 ->
                    if (it3.partOfSpeech.isNotBlank()) {
                        sb.append("partOfSpeech: " + it3.partOfSpeech + "\n")
                    }
                    it3.definitions.forEach { it4 ->
                        if (it4.definition.isNotBlank()) {
                            sb.append("definition: " + it4.definition + "\n")
                        }
                        if (it4.example.isNotBlank()) {
                            sb.append("example: " + it4.example + "\n\n")
                        }
                        sb.append("synonyms: " + it4.synonyms + "\n\n")


                        sb.append("antonyms: " + it4.antonyms + "\n\n")

                    }

                }
            }
            return sb

        } else {
            return sb
        }
    }

    private fun response(res: String): List<DictionaryResponse> {
        val listFinal: MutableList<DictionaryResponse> = ArrayList()
        try {

            var model: DictionaryResponse? = null
            val jsonArray = JSONArray(res)
            for (i in 0 until jsonArray.length()) {
                val phoneticsFinal: ArrayList<Phonetic> = ArrayList()
                val meaningsFinal: MutableList<Meaning> = ArrayList()


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
            return listFinal


        } catch (er: Exception) {
            return listFinal
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}