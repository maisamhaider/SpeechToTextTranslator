package com.example.speechtotexttranslator.ui.activities.cameratranslator

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.LANGUAGE_CAMERA_SUPPORTED
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.LANGUAGE_ONLINE
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.TEXT_SOURCE
import com.example.speechtotexttranslator.annotations.AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_CODE_CAMERA_TRANSLATOR
import com.example.speechtotexttranslator.databinding.ActivityCameraTranslatorResultBinding
import com.example.speechtotexttranslator.interfeces.TranslatorCallBack
import com.example.speechtotexttranslator.ui.activities.ActivityLanguages
import com.example.speechtotexttranslator.utils.AppPreferences.funGetString
import com.example.speechtotexttranslator.utils.Singleton.funCopy
import com.example.speechtotexttranslator.utils.Singleton.funLaunchLanguagesActivity
import com.example.speechtotexttranslator.utils.Singleton.funPaste
import com.example.speechtotexttranslator.utils.Singleton.funShare
import com.example.speechtotexttranslator.utils.Singleton.funTextToSpeech
import com.example.speechtotexttranslator.utils.Singleton.isInternet
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import com.example.speechtotexttranslator.utils.apis.OnlineTranslatorApi

class ActivityCameraTranslatorResult : AppCompatActivity(), TranslatorCallBack {
    var binding: ActivityCameraTranslatorResultBinding? = null

    var sourcesText: String? = null
    private var source = "en"
    private var target = "en"
    private var onlineTranslatorApi: OnlineTranslatorApi? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            ActivityCameraTranslatorResultBinding.inflate(LayoutInflater.from(this), null, false)
        setContentView(binding?.root)

        onlineTranslatorApi = OnlineTranslatorApi(this)

        sourcesText = intent.getStringExtra(TEXT_SOURCE)

        if (isInternet()) {
            source = funGetString(
                AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR,
                "en"
            )
            target = funGetString(
                AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR,
                "en"
            )
            onlineTranslatorApi!!.execute(sourcesText!!, source, target, this)

        } else {
            toastLong("No Internet connection")
        }


        binding?.apply {
            editTextInput.setText(sourcesText)
            imageButtonMic.setOnClickListener {
                source = funGetString(
                    AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR,
                    "en"
                )
                displaySpeechRecognizer(source)
            }
            textViewSourceLang.setOnClickListener {
                funLaunchLanguagesActivity(
                    LANGUAGE_CAMERA_SUPPORTED,
                    AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_CODE_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGES_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.SOURCE_RECENT_LANGUAGE_SELECTED_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_NAME_CAMERA_TRANSLATOR,
                    ActivityLanguages()
                )
            }
            textViewTargetLang.setOnClickListener {
                funLaunchLanguagesActivity(
                    LANGUAGE_ONLINE,
                    TARGET_RECENT_LANGUAGES_CODE_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGES_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.TARGET_RECENT_LANGUAGE_SELECTED_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR,
                    AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_NAME_CAMERA_TRANSLATOR,
                    ActivityLanguages()
                )
            }
            editTextInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0 == null || p0.filters.toString().isEmpty()) {
                        textViewResult.text = ""
                    }
                }
            })
            imageButtonPaste.setOnClickListener {
                editTextInput.setText(editTextInput.text.toString() + funPaste())
            }
            imageButtonCopySource.setOnClickListener {
                if (editTextInput.text.toString().isBlank()) {
                    toastLong("not input text")
                } else {
                    funCopy(editTextInput.text.toString())
                    toastLong("copied")
                }
            }
            imageButtonSpeakTarget.setOnClickListener {
                if (textViewResult.text.toString().isBlank()) {
                    toastLong("not input text")
                } else {
                    funTextToSpeech(textViewResult.text.toString(), target)
                }
            }
            imageButtonDeleteSource.setOnClickListener {
                editTextInput.setText("")
            }

            imageButtonCopyTarget.setOnClickListener {
                if (textViewResult.text.toString().isBlank()) {
                    toastLong("not text found")
                } else {
                    funCopy(textViewResult.text.toString())
                    toastLong("copied")
                }
            }
            textViewTranslate.setOnClickListener {
                if (isInternet()) {
                    source = funGetString(
                        AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR,
                        "en"
                    )
                    target = funGetString(
                        AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR,
                        "en"
                    )
                    sourcesText = editTextInput.text.toString()
                    if (sourcesText!!.isBlank()) {
                        toastLong("Please Enter word or sentence")
                    } else {
                        onlineTranslatorApi!!.execute(sourcesText!!,
                            source,
                            target,
                            this@ActivityCameraTranslatorResult)
                    }

                } else {
                    toastLong("No Internet connection")
                }
            }

            imageButtonShare.setOnClickListener {
                if (textViewResult.text.toString().isBlank()) {
                    toastLong("not text found")
                } else {
                    funShare(textViewResult.text.toString())
                }
            }

        }

    }

    private fun displaySpeechRecognizer(language: String) {
//        sR = Speech Recognizer
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
                binding?.editTextInput!!.setText(spokenText)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.textViewSourceLang!!.text = funGetString(
            AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_NAME_CAMERA_TRANSLATOR,
            "English"
        )
        binding?.textViewTargetLang!!.text = funGetString(
            AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_NAME_CAMERA_TRANSLATOR,
            "English"
        )
    }

    override fun call(result: String, source: String) {
        if (result.isNotEmpty()) {
            binding?.textViewResult!!.text = result
        } else {
            toastLong("text not found")
        }
    }

    override fun failure(messages: String) {
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}