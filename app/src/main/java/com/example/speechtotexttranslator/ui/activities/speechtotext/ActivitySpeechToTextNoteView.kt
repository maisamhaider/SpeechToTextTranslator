package com.example.speechtotexttranslator.ui.activities.speechtotext

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.INSERT_NOTE_SPEECH_TO_TEXT
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.SPEECH_TO_TEXT_NOTE_CODE
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.SPEECH_TO_TEXT_NOTE_ID
import com.example.speechtotexttranslator.annotations.AnNot.ObjLists.appPackages
import com.example.speechtotexttranslator.databinding.ActivitySpeechToNoteViewBinding
import com.example.speechtotexttranslator.db.speechtotext.ViewModelSpeechToText
import com.example.speechtotexttranslator.utils.Singleton.funShare
import com.example.speechtotexttranslator.utils.Singleton.funTextToSpeech
import com.example.speechtotexttranslator.utils.Singleton.initTTS
import com.example.speechtotexttranslator.utils.Singleton.sendIntent
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import com.example.speechtotexttranslator.utils.Singleton.ttsShutdown

class ActivitySpeechToTextNoteView : AppCompatActivity() {
    private var _binding: ActivitySpeechToNoteViewBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySpeechToNoteViewBinding.inflate(LayoutInflater.from(this), null,
            false)
        setContentView(binding.root)
        initTTS()


        val noteId = intent.getIntExtra(SPEECH_TO_TEXT_NOTE_ID, -1)
        val code = intent.getStringExtra(SPEECH_TO_TEXT_NOTE_CODE)
        var text = ""

        val viewModel: ViewModelSpeechToText = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelSpeechToText::class.java)

        binding.apply {
            viewModel.funGet(noteId).observe(this@ActivitySpeechToTextNoteView) {
                text = it.noteText!!
                textViewNoteTitle.text = it.noteTitle
                textTextInput.text = it.noteText
            }
            textViewNote.setOnClickListener {
                startActivity(Intent(this@ActivitySpeechToTextNoteView,
                    ActivitySpeechToText::class.java).apply {
                    putExtra(INSERT_NOTE_SPEECH_TO_TEXT, false)
                    putExtra(SPEECH_TO_TEXT_NOTE_ID, noteId)
                })
                finish()
            }
            imageButtonSocialMedia1.setOnClickListener {
                val app = appPackages(this@ActivitySpeechToTextNoteView)["WhatsApp"].toString()
                if (isPackageExists(app)) {
                    if (textTextInput.text.toString().isNotEmpty()) {
                        sendIntent(app, text)
                    } else {
                        toastLong("No text found")
                    }

                } else {
                    toastLong("App is not installed")
                }
            }
            imageButtonSocialMedia2.setOnClickListener {
                val app = appPackages(this@ActivitySpeechToTextNoteView)["Messenger"].toString()
                if (isPackageExists(app)) {
                    if (textTextInput.text.toString().isNotEmpty()) {
                        sendIntent(app, text)
                    } else {
                        toastLong("No text found")
                    }

                } else {
                    toastLong("App is not installed")
                }
            }
            imageButtonSocialMedia3.setOnClickListener {
                val app = appPackages(this@ActivitySpeechToTextNoteView)["Twitter"].toString()
                if (isPackageExists(app)) {
                    if (textTextInput.text.toString().isNotEmpty()) {
                        sendIntent(app, text)
                    } else {
                        toastLong("No text found")
                    }

                } else {
                    toastLong("App is not installed")
                }
            }
            imageButtonSocialMedia4.setOnClickListener {
                val app = appPackages(this@ActivitySpeechToTextNoteView)["imo"].toString()
                if (isPackageExists(app)) {
                    if (textTextInput.text.toString().isNotEmpty()) {
                        sendIntent(app, text)
                    } else {
                        toastLong("No text found")
                    }

                } else {
                    toastLong("App is not installed")
                }
            }
            imageButtonSocialMedia5.setOnClickListener {
                val app = appPackages(this@ActivitySpeechToTextNoteView)["Hangouts"].toString()
                if (isPackageExists(app)) {
                    if (textTextInput.text.toString().isNotEmpty()) {
                        sendIntent(app, text)

                    } else {
                        toastLong("No text found")
                    }

                } else {
                    toastLong("App is not installed")
                }
            }
            imageButtonSocialMedia6.setOnClickListener {
                val app = appPackages(this@ActivitySpeechToTextNoteView)["Messages"].toString()
                if (isPackageExists(app)) {
                    if (textTextInput.text.toString().isNotEmpty()) {
                        sendIntent(app, text)
                    } else {
                        toastLong("No text found")
                    }

                } else {
                    toastLong("App is not installed")
                }
            }
            imageButtonSocialMedia7.setOnClickListener {
                val app = appPackages(this@ActivitySpeechToTextNoteView)["Gmail"].toString()
                if (isPackageExists(app)) {
                    if (textTextInput.text.toString().isNotEmpty()) {
                        sendIntent(app, text)
                    } else {
                        toastLong("No text found")
                    }
                } else {
                    toastLong("App is not installed")
                }
            }
            imageButtonSocialMedia8.setOnClickListener {
                funShare(text)
            }
            textViewSpeak.setOnClickListener {
                if (textTextInput.text.toString().isNotEmpty()) {
                    funTextToSpeech(textTextInput.text.toString(), code!!)
                } else {
                    toastLong("No text found")
                }
            }
        }
    }

    private fun isPackageExists(app: String): Boolean {
        val ai = ApplicationInfo()
        ai.packageName = app
        return packageManager.getInstalledApplications(PackageManager.GET_META_DATA).contains(ai)
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsShutdown()
        _binding = null
    }
}