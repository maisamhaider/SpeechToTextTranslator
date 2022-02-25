package com.example.speechtotexttranslator.utils

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.speech.tts.TextToSpeech
import android.widget.Toast
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.interfeces.CallBackDownloadModel
import com.example.speechtotexttranslator.models.ModelQuizOfTheDay
import com.example.speechtotexttranslator.models.Option
import com.example.speechtotexttranslator.utils.AppPreferences.funAddStringSet
import com.example.speechtotexttranslator.utils.AppPreferences.funGetStringSet
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.util.*


object Singleton : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    lateinit var modelQuizOfTheDay: ModelQuizOfTheDay

    fun Context.initTTS() {
        if (tts == null) {
            tts = TextToSpeech(this, this@Singleton)
        }

        val engines = tts!!.engines
        var engine = tts!!.defaultEngine
        if (!engine.contains("google")) {
            engines.forEach {
                if (it.name.contains("google")) {
                    engine = it.name
                }
            }
        }
        tts = TextToSpeech(this, this@Singleton, engine)
    }

    fun Context.toastLong(messages: String) {
        Toast.makeText(this, messages, Toast.LENGTH_LONG).show()
    }

    fun Context.toastShort(messages: String) {
        Toast.makeText(this, messages, Toast.LENGTH_SHORT).show()
    }

    fun Context.isInternet(): Boolean {
        val internet = Internet(this)
        return internet.isInternetAvailable(this)
    }

    fun Context.funCopy(text: String) {
        val clipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData = ClipData.newPlainText("", text)
        clipboardManager.setPrimaryClip(clipData)
    }

    fun Context.sendIntent(app: String, text: String) {
        val sendIntent = Intent()
        sendIntent.action = Intent.ACTION_SEND
        sendIntent.putExtra(Intent.EXTRA_TEXT, text)
        sendIntent.type = "text/plain"
        sendIntent.setPackage(app)
        startActivity(sendIntent)
    }

    fun Context.initQuizOfTheDay() {

        val raw = resources.openRawResource(R.raw.quiz_of_the_day)
        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        raw.use { rawData ->
            val reader: Reader = BufferedReader(InputStreamReader(rawData, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        }

        val jsonString = writer.toString()

        val cal = Calendar.getInstance()
        val today = cal.get(Calendar.DAY_OF_YEAR)
        val jsonArray = JSONArray(jsonString)

        val optionsArray: ArrayList<Option> = ArrayList()
        val jsonObject: JSONObject = jsonArray.getJSONObject(today)

        val word = jsonObject.getString("word").toString()
        val answer = jsonObject.getString("answer").toString()
        val options: JSONArray = jsonObject.getJSONArray("options")
        val optionsObject: JSONObject = options.getJSONObject(0)

        optionsArray.add(
            Option(
                optionsObject.getString("option_1"),
                optionsObject.getString("option_2"),
                optionsObject.getString("option_3"),
                optionsObject.getString("option_4")
            )
        )

        modelQuizOfTheDay = ModelQuizOfTheDay(word, answer, optionsArray)

    }

    fun Context.getQuizOfTheDay(): ModelQuizOfTheDay {
        return modelQuizOfTheDay
    }


    fun Context.funTextToSpeech(text: String, code: String) {

        if (!tts!!.isSpeaking) {
            tts!!.setSpeechRate(1f)
            val lng = Locale.getAvailableLocales()
            val l = if (lng.contains(Locale.forLanguageTag(code))) {
                Locale.forLanguageTag(code)
            } else {
                Locale.US
            }
            tts!!.setSpeechRate(1f)
            tts!!.language = l
            tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }


    }

    fun Context.ttsShutdown() {
        if (tts!!.isSpeaking) {
            tts!!.stop()
        }
    }


    fun Context.funPaste(): String {
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager?
        var pasteData = ""

        // If it does contain data, decide if you can handle the data.

        // If it does contain data, decide if you can handle the data.
        if (!clipboard!!.hasPrimaryClip()) {
        } else if (!clipboard.primaryClipDescription!!.hasMimeType(MIMETYPE_TEXT_PLAIN)) {
            // since the clipboard has data but it is not plain text
        } else {
            //since the clipboard contains plain text.
            val item = clipboard.primaryClip!!.getItemAt(0)

            // Gets the clipboard as text.
            pasteData = item.text.toString()
        }
        return pasteData
    }

    fun Context.funShare(text: String) {
        val intentShare = Intent()
        intentShare.action = Intent.ACTION_SEND
        intentShare.type = "text/plain"
        intentShare.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(intentShare)
    }

    fun Context.funShowDownloadDialog(
        title: String,
        message: String,
        buttonPos: String,
        buttonNeg: String,
        calBack: CallBackDownloadModel,
        activity: Activity,
    ) {
        val builder = AlertDialog.Builder(this)
            .setTitle(title).setMessage(message).setCancelable(true).setPositiveButton(
                buttonPos
            ) { dialogInterface, _ ->
                calBack.buttonClicked(null)
                dialogInterface.dismiss()
            }.setNegativeButton(buttonNeg) { dialog, _ ->
                dialog.dismiss()
                activity.finish()
            }
        builder.create().show()
    }

    fun Context.funAddToRecentSetPref(key: String, name: String) {
        val recentSet: MutableSet<String> = funGetStringSet(key, setOf("English"))
        val list = ArrayList<String>()
        list.addAll(recentSet)

        recentSet.removeAll(list)
        if (list.size >= 4) {
            list.removeFirst()
        }
        list.add(name)
        recentSet.addAll(list)



        funAddStringSet(key, recentSet)
    }

    fun Context.funLaunchLanguagesActivity(
        sourceLanguagesList: String,
        recentLanguagesCodeList: String,
        recentLanguagesKey: String,
        recentLanguageKey: String,
        sourceLanguageCodeKey: String?,
        targetLanguageNameKey: String?,
        activity: Activity,
    ) {
        val intent = Intent(this, activity::class.java)
        intent.putExtra(
            AnNot.ObjIntentKeys.WHICH_RECENT_LANGUAGE_CODE_LIST,
            recentLanguagesCodeList
        )
        intent.putExtra(AnNot.ObjIntentKeys.WHICH_LANGUAGE_CODE, sourceLanguageCodeKey)
        intent.putExtra(AnNot.ObjIntentKeys.WHICH_LANGUAGE_CODE, sourceLanguageCodeKey)
        intent.putExtra(AnNot.ObjIntentKeys.WHICH_LANGUAGE_NAME, targetLanguageNameKey)
        intent.putExtra(AnNot.ObjIntentKeys.WHICH_RECENT_LANGUAGE, recentLanguageKey)
        intent.putExtra(AnNot.ObjIntentKeys.WHICH_RECENT_LANGUAGE_LIST, recentLanguagesKey)
        intent.putExtra(AnNot.ObjIntentKeys.SOURCE_LANGUAGE_LIST, sourceLanguagesList)
        startActivity(intent)
    }

    override fun onInit(p0: Int) {
    }

}