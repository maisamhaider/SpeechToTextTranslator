package com.example.speechtotexttranslator.ui.activities.speechtotext

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.INSERT_NOTE_SPEECH_TO_TEXT
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.LANGUAGE_ONLINE
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.SPEECH_TO_TEXT_NOTE_ID
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.AUTO_SPEAK_TEXT_TO_SPEECH
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_CODE_SPEECH_TO_TEXT
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_NAME_SPEECH_TO_TEXT
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_CODE_SPEECH_TO_TEXT
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_SPEECH_TO_TEXT
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGE_SPEECH_TO_TEXT
import com.example.speechtotexttranslator.databinding.ActivitySpeechToTextBinding
import com.example.speechtotexttranslator.db.speechtotext.ViewModelSpeechToText
import com.example.speechtotexttranslator.models.ModelSpeechToTextNote
import com.example.speechtotexttranslator.ui.activities.ActivityLanguages
import com.example.speechtotexttranslator.utils.AppPreferences.funAddBoolean
import com.example.speechtotexttranslator.utils.AppPreferences.funGetBoolean
import com.example.speechtotexttranslator.utils.AppPreferences.funGetString
import com.example.speechtotexttranslator.utils.Singleton.funCopy
import com.example.speechtotexttranslator.utils.Singleton.funLaunchLanguagesActivity
import com.example.speechtotexttranslator.utils.Singleton.funPaste
import com.example.speechtotexttranslator.utils.Singleton.funShare
import com.example.speechtotexttranslator.utils.Singleton.funTextToSpeech
import com.example.speechtotexttranslator.utils.Singleton.initTTS
import com.example.speechtotexttranslator.utils.Singleton.isInternet
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import com.example.speechtotexttranslator.utils.Singleton.ttsShutdown
import java.text.SimpleDateFormat

class ActivitySpeechToText : AppCompatActivity() {

    private var _binding: ActivitySpeechToTextBinding? = null
    private val binding get() = _binding!!
    private var viewModel: ViewModelSpeechToText? = null

    private var insert: Boolean = true

    private var noteId: Int = -1
    private var noteTitle: String = "Abc"
    private var noteLanguage: String = "English"
    private var noteDate: String = "1/1/1990"
    private var noteText: String = "text"

    private var langButtonClicked: Boolean = false
    private var langBeforeClick: String = "English"

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySpeechToTextBinding.inflate(LayoutInflater.from(this), null,
            false)
        setContentView(binding.root)
        initTTS()
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(ViewModelSpeechToText::class.java)



        langBeforeClick = funGetString(SOURCE_LANGUAGE_SELECTED_NAME_SPEECH_TO_TEXT, "English")

        binding.apply {
            clSaved.setOnClickListener {
                startActivity(Intent(this@ActivitySpeechToText,
                    ActivitySpeechToTextNotesList::class.java))
            }
            textViewAutoSpeak.setOnClickListener {
                val autoSpeak = funGetBoolean(AUTO_SPEAK_TEXT_TO_SPEECH, false)
                if (autoSpeak) {
                    textViewAutoSpeak.setBackgroundColor(
                        ContextCompat.getColor(applicationContext, R.color.white))
                    funAddBoolean(AUTO_SPEAK_TEXT_TO_SPEECH, false)
                } else {
                    textViewAutoSpeak.setBackgroundColor(
                        ContextCompat.getColor(applicationContext, R.color.teal_700))
                    funAddBoolean(AUTO_SPEAK_TEXT_TO_SPEECH, true)
                }
            }
            textViewSourceLanguage.setOnClickListener {
                if (noteId != -1) {
                    langButtonClicked = true
                    langBeforeClick =
                        funGetString(SOURCE_LANGUAGE_SELECTED_NAME_SPEECH_TO_TEXT, "English")
                }
                funLaunchLanguagesActivity(
                    LANGUAGE_ONLINE,
                    SOURCE_RECENT_LANGUAGES_CODE_SPEECH_TO_TEXT,
                    SOURCE_RECENT_LANGUAGES_SPEECH_TO_TEXT,
                    SOURCE_RECENT_LANGUAGE_SPEECH_TO_TEXT,
                    SOURCE_LANGUAGE_SELECTED_CODE_SPEECH_TO_TEXT,
                    SOURCE_LANGUAGE_SELECTED_NAME_SPEECH_TO_TEXT,
                    ActivityLanguages()
                )
            }

            imageButtonDelete.setOnClickListener {
                editTextInput.setText("")
            }
            imageButtonCopy.setOnClickListener {
                val text = editTextInput.text.toString()
                if (text.isNotEmpty()) {
                    funCopy(text)
                    toastLong("copied")
                } else {
                    toastLong("No input found")
                }
            }
            imageButtonPaste.setOnClickListener {
                val text = funPaste()
                if (text.isNotEmpty()) {
                    editTextInput.setText(editTextInput.text.toString() + text)
                    if (!imageButtonDelete.isVisible && !imageButtonCopy.isVisible) {
                        imageButtonDelete.visibility = VISIBLE
                        imageButtonCopy.visibility = VISIBLE
                    }
                } else {
                    toastLong("No copied data in clipboard")
                }
            }
            imageButtonShare.setOnClickListener {
                val text = editTextInput.text.toString()
                if (text.isNotEmpty()) {
                    funShare(text)
                } else {
                    toastLong("No input text")
                }
            }

            textViewMic.setOnClickListener {
                if (isInternet()) {
                    val sourceCode =
                        funGetString(SOURCE_LANGUAGE_SELECTED_CODE_SPEECH_TO_TEXT, "en")
                    displaySpeechRecognizer(sourceCode)
                } else {
                    toastLong("No Internet connection")
                }
            }
            textViewNote.setOnClickListener {
                noteText = editTextInput.text.toString()
                if (noteText.isNotEmpty()) {
                    if (insert) {
                        noteInsertDialog()
                    } else {
                        //update
                        if (noteId > -1) {
                            val sourceCode =
                                funGetString(SOURCE_LANGUAGE_SELECTED_CODE_SPEECH_TO_TEXT,
                                    "en")
                            val model =
                                ModelSpeechToTextNote(
                                    noteTitle,
                                    noteDate,
                                    noteLanguage,
                                    sourceCode,
                                    noteText,
                                )
                            model.id = noteId
                            viewModel!!.funUpdate(model)
                            toastLong("note updated")
                            finish()
                        } else {
                            Log.e("ActivitySpeechToText", "invalid_id_________________${noteId}")
                        }
                    }
                } else {
                    toastLong("text not found")
                }
            }
            imageButtonSocialMedia1.setOnClickListener {
                toastLong("text will be share to social media1")
            }
            imageButtonSocialMedia2.setOnClickListener {
                toastLong("text will be share to social media2")
            }
            imageButtonSocialMedia3.setOnClickListener {
                toastLong("text will be share to social media3")
            }
            imageButtonSocialMedia4.setOnClickListener {
                toastLong("text will be share to social media4")
            }
            imageButtonSocialMedia5.setOnClickListener {
                toastLong("text will be share to social media5")
            }
            imageButtonSocialMedia6.setOnClickListener {
                toastLong("text will be share to social media6")
            }
            imageButtonSocialMedia7.setOnClickListener {
                toastLong("text will be share to social media7")
            }
        }
    }

    private fun displaySpeechRecognizer(language: String) {

        val sRIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            ).putExtra(RecognizerIntent.EXTRA_LANGUAGE, language)
        }
        intentLauncher.launch(sRIntent)
    }

    private var intentLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            if (result.data != null) {
                val spokenText: String =
                    result.data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        .let { results ->
                            results!![0]
                        }
                binding.editTextInput.setText(spokenText)
                if (funGetBoolean(AUTO_SPEAK_TEXT_TO_SPEECH, false)) {
                    if (spokenText.isNotBlank()) {
                        val sourceCode =
                            funGetString(SOURCE_LANGUAGE_SELECTED_CODE_SPEECH_TO_TEXT, "en")
                        funTextToSpeech(spokenText, sourceCode)
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun noteInsertDialog() {
        val view = LayoutInflater.from(this).inflate(
            R.layout.layout_dialog_note_title,
            null
        )
        val textViewCancel: TextView = view.findViewById(R.id.textViewCancel)
        val textViewSave: TextView = view.findViewById(R.id.textViewSave)
        val editTextNoteTitle: EditText = view.findViewById(R.id.editTextNoteTitle)

        val builder: AlertDialog.Builder = AlertDialog.Builder(this).apply {
            setView(view)
        }

        val alertdialog: AlertDialog = builder.create()
        alertdialog.show()
        textViewCancel.setOnClickListener {
            alertdialog.cancel()
        }
        textViewSave.setOnClickListener {
            val noteText = binding.editTextInput.text.toString()
            val noteTitle = editTextNoteTitle.text.toString()
            val sp = SimpleDateFormat("dd/MM/yyyy")
            val noteDate = sp.format(System.currentTimeMillis())
            val noteLanguage =
                funGetString(SOURCE_LANGUAGE_SELECTED_NAME_SPEECH_TO_TEXT, "English")

            if (noteTitle.isNotEmpty()) {
                val sourceCode =
                    funGetString(SOURCE_LANGUAGE_SELECTED_CODE_SPEECH_TO_TEXT, "en")
                val model =
                    ModelSpeechToTextNote(noteTitle, noteDate, noteLanguage, sourceCode, noteText)
                viewModel!!.funInsert(model)
                toastLong("note created")
                alertdialog.cancel()
            } else {
                toastLong("Enter note title")
            }

        }
    }

    override fun onResume() {
        super.onResume()
        noteLanguage = funGetString(SOURCE_LANGUAGE_SELECTED_NAME_SPEECH_TO_TEXT, "English")
        insert = intent.getBooleanExtra(INSERT_NOTE_SPEECH_TO_TEXT, true)
        binding.editTextInput.setText(binding.editTextInput.text.toString())

        if (insert) {
            binding.textViewSourceLanguage.text = noteLanguage
        } else {
            noteId = intent.getIntExtra(SPEECH_TO_TEXT_NOTE_ID, -1)
            binding.textViewNote.text = "Update Note"
            viewModel!!.funGet(noteId).observe(this) {
                if (langBeforeClick == noteLanguage) {
                    noteLanguage = it.noteLanguage.toString()
                    binding.editTextInput
                }
                if (!langButtonClicked) {
                    binding.editTextInput.setText(it.noteText)
                }
                binding.editTextInput
                binding.textViewSourceLanguage.text = noteLanguage
                noteTitle = it.noteTitle.toString()
                noteDate = it.noteDate.toString()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsShutdown()
        _binding = null
    }
}