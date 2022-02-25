package com.example.speechtotexttranslator.annotations

import android.content.Context
import android.provider.Telephony
import com.example.speechtotexttranslator.models.ModelLanguage
import org.intellij.lang.annotations.Language
import org.jetbrains.annotations.NotNull

annotation class AnNot {

    object ObjRoomItems {
        const val TRANSLATED_RESULTS_OFFLINE = "TRANSLATED_RESULTS_OFFLINE"
        const val TABLE_TRANSLATED_RESULTS_SPEAK_AND_TRANSLATE =
            "TABLE_TRANSLATED_RESULTS_SPEAK_AND_TRANSLATE"
        const val TABLE_FAVORITES_VOICE_TRANSLATOR = "TABLE_FAVORITES_VOICE_TRANSLATOR"
        const val TABLE_HISTORY_VOICE_TRANSLATOR = "TABLE_HISTORY_VOICE_TRANSLATOR"
        const val TABLE_NOTE_SPEECH_TO_TEXT = "TABLE_NOTE_SPEECH_TO_TEXT"
        const val TABLE_USE_FULL_PHRASES_FAVORITE = "TABLE_USE_FULL_PHRASES_FAVORITE"
        const val TABLE_DICTIONARY_HISTORY = "TABLE_DICTIONARY_HISTORY"
        const val TABLE_DICTIONARY_FAVORITES = "TABLE_DICTIONARY_FAVORITES"
        const val TABLE_RECENT_LANGUAGES = "TABLE_RECENT_LANGUAGES"
    }

    object ObjIntentKeys {
        const val SPEECH_TO_TEXT_NOTE_CODE = "SPEECH_TO_TEXT_NOTE_CODE"
        const val SOURCE_LANGUAGE_LIST = "SOURCE_LANGUAGE_LIST"
        const val IS_SOURCE = "IS_SOURCE"
        const val WHICH_LANGUAGE_CODE = "WHICH_LANGUAGE_CODE"
        const val WHICH_LANGUAGE_NAME = "WHICH_LANGUAGE"
        const val WHICH_LANGUAGE = "WHICH_LANGUAGE"
        const val WHICH_RECENT_LANGUAGE = "WHICH_RECENT_LANGUAGE"
        const val WHICH_RECENT_LANGUAGE_LIST = "WHICH_RECENT_LANGUAGE_LIST"
        const val WHICH_RECENT_LANGUAGE_CODE_LIST = "WHICH_RECENT_LANGUAGE_CODE_LIST"
        const val WHICH_BUNDLE = "WHICH_BUNDLE"
        const val IS_ONLINE = "IS_ONLINE"
        const val TEXT_SOURCE = "TEXT_SOURCE"
        const val CODE_SOURCE = "CODE_SOURCE"
        const val TEXT_RESULT = "TEXT_RESULT"
        const val CODE_RESULT = "CODE_RESULT"
        const val SOURCE_LANGUAGE = "SOURCE_LANGUAGE"
        const val TARGET_LANGUAGE = "TARGET_LANGUAGE"
        const val IS_VOICE_TRANSLATOR_FAVORITE_RESULT = "IS_VOICE_TRANSLATOR_FAVORITE_RESULT"
        const val VOICE_TRANSLATOR_FAVORITE_RESULT_ID = "VOICE_TRANSLATOR_FAVORITE_RESULT_ID"

        const val INSERT_NOTE_SPEECH_TO_TEXT = "INSERT_NOTE_SPEECH_TO_TEXT"
        const val SPEECH_TO_TEXT_NOTE_ID = "SPEECH_TO_TEXT_NOTE_ID"
        const val IMAGE_URI = "IMAGE_URI"
        const val USEFUL_PHRASES_LIST_NO = "USEFUL_PHRASES_LIST_NO"
        const val WORD = "WORD"
        const val TEXT = "TEXT"


        const val LANGUAGE_ONLINE = "LANGUAGE_ONLINE"
        const val LANGUAGE_OFFLINE = "LANGUAGE_OFFLINE"
        const val LANGUAGE_CAMERA_SUPPORTED = "LANGUAGE_CAMERA_SUPPORTED"


    }

    object ObjNames {
        const val APP_PREFERENCES = "APP_PREFERENCES"
        const val SOURCE_LANGUAGE = "SOURCE_LANGUAGE"
        const val TARGET_LANGUAGE = "TARGET_LANGUAGE"

    }


    object ObjPreferencesKeys {

        const val SOURCE_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE =
            "SOURCE_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE"
        const val SOURCE_LANGUAGE_SELECTED_NAME_SPEAK_AND_TRANSLATE =
            "SOURCE_LANGUAGE_SELECTED_NAME_SPEAK_AND_TRANSLATE"

        const val TARGET_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE =
            "TARGET_LANGUAGE_SELECTED_CODE_SPEAK_AND_TRANSLATE"
        const val TARGET_LANGUAGE_SELECTED_NAME_SPEAK_AND_TRANSLATE =
            "TARGET_LANGUAGE_SELECTED_NAME_SPEAK_AND_TRANSLATE"

        const val SOURCE_RECENT_LANGUAGES_CODE_SPEAK_AND_TRANSLATE =
            "SOURCE_RECENT_LANGUAGES_CODE_SPEAK_AND_TRANSLATE"
        const val SOURCE_RECENT_LANGUAGES_SPEAK_AND_TRANSLATE =
            "SOURCE_RECENT_LANGUAGES_SPEAK_AND_TRANSLATE"
        const val TARGET_RECENT_LANGUAGES_SPEAK_AND_TRANSLATE =
            "TARGET_RECENT_LANGUAGES_SPEAK_AND_TRANSLATE"
        const val TARGET_RECENT_LANGUAGES_CODE_SPEAK_AND_TRANSLATE =
            "TARGET_RECENT_LANGUAGES_CODE_SPEAK_AND_TRANSLATE"

        const val SOURCE_RECENT_LANGUAGE_SELECTED_SPEAK_AND_TRANSLATE =
            "SOURCE_RECENT_LANGUAGE_SELECTED_SPEAK_AND_TRANSLATE"
        const val TARGET_RECENT_LANGUAGE_SELECTED_SPEAK_AND_TRANSLATE =
            "TARGET_RECENT_LANGUAGE_SELECTED_SPEAK_AND_TRANSLATE"

        const val DOWNLOADED_MODELS = "DOWNLOADED_MODELS"


        const val SOURCE_RECENT_LANGUAGES_OFFLINE_TRANSLATOR_CODE =
            "SOURCE_RECENT_LANGUAGES_OFFLINE_TRANSLATOR_CODE"
        const val TARGET_RECENT_LANGUAGES_OFFLINE_TRANSLATOR_CODE =
            "TARGET_RECENT_LANGUAGES_OFFLINE_TRANSLATOR_CODE"

        const val SOURCE_RECENT_LANGUAGES_OFFLINE_TRANSLATOR =
            "SOURCE_RECENT_LANGUAGES_OFFLINE_TRANSLATOR"
        const val TARGET_RECENT_LANGUAGES_OFFLINE_TRANSLATOR =
            "TARGET_RECENT_LANGUAGES_OFFLINE_TRANSLATOR"

        const val SOURCE_RECENT_LANGUAGE_SELECTED_OFFLINE_TRANSLATOR =
            "SOURCE_RECENT_LANGUAGE_SELECTED_OFFLINE_TRANSLATOR"
        const val TARGET_RECENT_LANGUAGE_SELECTED_OFFLINE_TRANSLATOR =
            "TARGET_RECENT_LANGUAGE_SELECTED_OFFLINE_TRANSLATOR"

        const val SOURCE_LANGUAGE_SELECTED_CODE_OFFLINE_TRANSLATOR =
            "SOURCE_LANGUAGE_SELECTED_CODE_OFFLINE_TRANSLATOR"
        const val SOURCE_LANGUAGE_SELECTED_NAME_OFFLINE_TRANSLATOR =
            "SOURCE_LANGUAGE_SELECTED_NAME_OFFLINE_TRANSLATOR"

        const val TARGET_LANGUAGE_SELECTED_CODE_OFFLINE_TRANSLATOR =
            "TARGET_LANGUAGE_SELECTED_CODE_OFFLINE_TRANSLATOR"
        const val TARGET_LANGUAGE_SELECTED_NAME_OFFLINE_TRANSLATOR =
            "TARGET_LANGUAGE_SELECTED_NAME_OFFLINE_TRANSLATOR"

        const val SOURCE_RECENT_LANGUAGES_VOICE_TRANSLATOR =
            "SOURCE_RECENT_LANGUAGES_VOICE_TRANSLATOR"
        const val SOURCE_RECENT_LANGUAGES_CODE_VOICE_TRANSLATOR =
            "SOURCE_RECENT_LANGUAGES_CODE_VOICE_TRANSLATOR"
        const val SOURCE_RECENT_LANGUAGE_SELECTED_VOICE_TRANSLATOR =
            "SOURCE_RECENT_LANGUAGE_SELECTED_VOICE_TRANSLATOR"
        const val SOURCE_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR =
            "SOURCE_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR"
        const val SOURCE_LANGUAGE_SELECTED_NAME_VOICE_TRANSLATOR =
            "SOURCE_LANGUAGE_SELECTED_NAME_VOICE_TRANSLATOR"

        const val TARGET_RECENT_LANGUAGES_VOICE_TRANSLATOR =
            "TARGET_RECENT_LANGUAGES_VOICE_TRANSLATOR"
        const val TARGET_RECENT_LANGUAGES_CODE_VOICE_TRANSLATOR =
            "TARGET_RECENT_LANGUAGES_CODE_VOICE_TRANSLATOR"
        const val TARGET_RECENT_LANGUAGE_SELECTED_VOICE_TRANSLATOR =
            "TARGET_RECENT_LANGUAGE_SELECTED_VOICE_TRANSLATOR"
        const val TARGET_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR =
            "TARGET_LANGUAGE_SELECTED_CODE_VOICE_TRANSLATOR"
        const val TARGET_LANGUAGE_SELECTED_NAME_VOICE_TRANSLATOR =
            "TARGET_LANGUAGE_SELECTED_NAME_VOICE_TRANSLATOR"

        const val SOURCE_RECENT_LANGUAGES_SPEECH_TO_TEXT =
            "SOURCE_RECENT_LANGUAGES_SPEECH_TO_TEXT"
        const val SOURCE_RECENT_LANGUAGES_CODE_SPEECH_TO_TEXT =
            "SOURCE_RECENT_LANGUAGES_CODE_SPEECH_TO_TEXT"
        const val SOURCE_RECENT_LANGUAGE_SPEECH_TO_TEXT =
            "SOURCE_RECENT_LANGUAGE_SPEECH_TO_TEXT"
        const val SOURCE_LANGUAGE_SELECTED_CODE_SPEECH_TO_TEXT =
            "SOURCE_LANGUAGE_SELECTED_CODE_SPEECH_TO_TEXT"
        const val SOURCE_LANGUAGE_SELECTED_NAME_SPEECH_TO_TEXT =
            "SOURCE_LANGUAGE_SELECTED_NAME_SPEECH_TO_TEXT"


        const val SOURCE_RECENT_LANGUAGES_CODE_CAMERA_TRANSLATOR =
            "SOURCE_RECENT_LANGUAGES_CODE_CAMERA_TRANSLATOR"
        const val SOURCE_RECENT_LANGUAGES_CAMERA_TRANSLATOR =
            "SOURCE_RECENT_LANGUAGES_CAMERA_TRANSLATOR"
        const val SOURCE_RECENT_LANGUAGE_SELECTED_CAMERA_TRANSLATOR =
            "SOURCE_RECENT_LANGUAGE_SELECTED_CAMERA_TRANSLATOR"
        const val SOURCE_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR =
            "SOURCE_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR"
        const val SOURCE_LANGUAGE_SELECTED_NAME_CAMERA_TRANSLATOR =
            "SOURCE_LANGUAGE_SELECTED_NAME_CAMERA_TRANSLATOR"

        const val TARGET_RECENT_LANGUAGES_CODE_CAMERA_TRANSLATOR =
            "TARGET_RECENT_LANGUAGES_CODE_CAMERA_TRANSLATOR"
        const val TARGET_RECENT_LANGUAGES_CAMERA_TRANSLATOR =
            "TARGET_RECENT_LANGUAGES_CAMERA_TRANSLATOR"
        const val TARGET_RECENT_LANGUAGE_SELECTED_CAMERA_TRANSLATOR =
            "TARGET_RECENT_LANGUAGE_SELECTED_CAMERA_TRANSLATOR"
        const val TARGET_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR =
            "TARGET_LANGUAGE_SELECTED_CODE_CAMERA_TRANSLATOR"
        const val TARGET_LANGUAGE_SELECTED_NAME_CAMERA_TRANSLATOR =
            "TARGET_LANGUAGE_SELECTED_NAME_CAMERA_TRANSLATOR"

        const val SOURCE_RECENT_LANGUAGES_USEFUL_PHRASES =
            "SOURCE_RECENT_LANGUAGES_CAMERA_TRANSLATOR"
        const val SOURCE_RECENT_LANGUAGES_CODE_USEFUL_PHRASES =
            "SOURCE_RECENT_LANGUAGES_CODE_USEFUL_PHRASES"
        const val SOURCE_RECENT_LANGUAGE_SELECTED_USEFUL_PHRASES =
            "SOURCE_RECENT_LANGUAGE_SELECTED_USEFUL_PHRASES"
        const val SOURCE_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES =
            "SOURCE_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES"
        const val SOURCE_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES =
            "SOURCE_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES"

        const val TARGET_RECENT_LANGUAGES_CODE_USEFUL_PHRASES =
            "TARGET_RECENT_LANGUAGES_CODE_USEFUL_PHRASES"
        const val TARGET_RECENT_LANGUAGES_USEFUL_PHRASES =
            "TARGET_RECENT_LANGUAGES_USEFUL_PHRASES"
        const val TARGET_RECENT_LANGUAGE_SELECTED_USEFUL_PHRASES =
            "TARGET_RECENT_LANGUAGE_SELECTED_USEFUL_PHRASES"
        const val TARGET_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES =
            "TARGET_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES"
        const val TARGET_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES =
            "TARGET_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES"

        const val TARGET_RECENT_LANGUAGES_CODE_DICTIONARY_RESULT =
            "TARGET_RECENT_LANGUAGES_CODE_DICTIONARY_RESULT"
        const val TARGET_RECENT_LANGUAGES_DICTIONARY_RESULT =
            "TARGET_RECENT_LANGUAGES_DICTIONARY_RESULT"
        const val TARGET_RECENT_LANGUAGE_SELECTED_DICTIONARY_RESULT =
            "TARGET_RECENT_LANGUAGE_SELECTED_DICTIONARY_RESULT"
        const val TARGET_LANGUAGE_SELECTED_CODE_DICTIONARY_RESULT =
            "TARGET_LANGUAGE_SELECTED_CODE_DICTIONARY_RESULT"
        const val TARGET_LANGUAGE_SELECTED_NAME_DICTIONARY_RESULT =
            "TARGET_LANGUAGE_SELECTED_NAME_DICTIONARY_RESULT"

        const val AUTO_SPEAK_TEXT_TO_SPEECH =
            "AUTO_SPEAK_TEXT_TO_SPEECH"


    }

    object ObjLists {
        private val LANGUAGES_CODES = listOf(
            "af", "ar", "be", "bg", "bn", "ca", "cs", "cy", "da",
            "de", "el", "en", "eo", "es", "et", "fa", "fi", "fr", "ga", "gl", "gu", "he", "hi",
            "hr", "ht", "hu", "id", "is", "it", "ja", "ka", "kn", "ko", "lt", "lv", "mk", "mr",
            "ms", "mt", "nl", "no", "pl", "pt", "ro", "ru", "sk", "sl", "sq", "sv", "sw", "ta",
            "te", "th", "tl", "tr", "uk", "ur", "vi", "zh"
        )
        private val LANGUAGES_CODES_ONLINE = listOf(
            "af", "sq", "am", "ar", "hy", "az", "eu", "be", "bn", "bs", "bg", "ca", "ceb", "zh-CN",
            "zh-TW", "co", "hr", "cs", "da", "nl", "en", "eo", "et", "fi", "fr", "fy", "gl", "ka",
            "de", "el", "gu", "ht", "ha", "haw", "he", "hi", "hmn", "hu", "is", "ig", "id", "ga",
            "it", "ja", "jv", "kn", "kk", "km", "rw", "ko", "ku", "ky", "lo", "lv", "lt", "lb",
            "mk", "mg", "ms", "ml", "mt", "mi", "mr", "mn", "my", "ne", "no", "ny", "or", "ps",
            "fa", "pl", "pt", "pa", "ro", "ru", "sm", "gd", "sr", "st", "sn", "sd", "si", "sk",
            "sl", "so", "es", "su", "sw", "sv", "tl", "tg", "ta", "tt", "te", "th", "tr", "tk",
            "uk", "ur", "ug", "uz", "vi", "cy", "xh", "yi", "yo", "zu"
        )

        private val LANGUAGES_NAMES = listOf(
            "Afrikaans", "Arabic", "Belarusian", "Bulgarian",
            "Bengali", "Catalan", "Czech", "Welsh", "Danish", "German", "Greek", "English",
            "Esperanto", "Spanish", "Estonian", "Persian", "Finnish", "French", "Irish", "Galician",
            "Gujarati", "Hebrew", "Hindi", "Croatian", "Haitian", "Hungarian", "Indonesian",
            "Icelandic", "Italian", "Japanese", "Georgian", "Kannada", "Korean", "Lithuanian",
            "Latvian", "Macedonian", "Marathi", "Malay", "Maltese", "Dutch", "Norwegian", "Polish",
            "Portuguese", "Romanian", "Russian", "Slovak", "Slovenian", "Albanian", "Swedish",
            "Swahili", "Tamil", "Telugu", "Thai", "Tagalog", "Turkish", "Ukrainian", "Urdu",
            "Vietnamese", "Chinese"
        )
        private val LANGUAGES_CODES_CAMERA_SUPPORTED = listOf(
            "af", "sq", "ca", "hr", "cs", "da", "nl", "en", "et", "tl", "fi", "fr", "de", "hu",
            "is", "id", "it", "lv", "lt", "ms", "no", "pl", "pt", "ro", "sr", "sk", "sl",
            "es", "sv", "tr", "vi"
        )
        private val LANGUAGES_NAMES_CAMERA_SUPPORTED = listOf(
            "Afrikaans",
            "Albanian",
            "Catalan",
            "Croatian",
            "Czech",
            "Danish",
            "Dutch",
            "English",
            "Estonian",
            "Filipino",
            "Finnish",
            "French",
            "German",
            "Hungarian",
            "Icelandic",
            "Indonesian",
            "Italian",
            "Latvian",
            "Lithuanian",
            "Malay",
            "Norwegian",
            "Polish",
            "Portuguese",
            "Romanian",
            "Serbian",
            "Slovak",
            "Slovenian",
            "Spanish",
            "Swedish",
            "Turkish",
            "Vietnamese"
        )

        fun appPackages(context: Context): HashMap<String, String> {
            val apps = HashMap<String, String>()
            return apps.apply {
                set("WhatsApp", "com.whatsapp")
                set("Messenger ", "com.facebook.orca")
                set("Twitter", "com.twitter.android")
                set("imo", "com.imo.android.imoim")
                set("Hangouts", "com.google.android.talk")
                set("Messages", Telephony.Sms.getDefaultSmsPackage(context))
                set("Gmail", "com.google.android.gm")
            }
        }

        private val LANGUAGES_NAMES_ONLINE = listOf(
            "Afrikaans",
            "Albanian",
            "Amharic",
            "Arabic",
            "Armenian",
            "Azerbaijani",
            "Basque",
            "Belarusian",
            "Bengali",
            "Bosnian",
            "Bulgarian",
            "Catalan",
            "Cebuano",
            "Chinese (Simplified)",
            "Chinese (Traditional)",
            "Corsican",
            "Croatian",
            "Czech",
            "Danish",
            "Dutch",
            "English",
            "Esperanto",
            "Estonian",
            "Finnish",
            "French",
            "Frisian",
            "Galician",
            "Georgian",
            "German",
            "Greek",
            "Gujarati",
            "Haitian Creole",
            "Hausa",
            "Hawaiian",
            "Hebrew",
            "Hindi",
            "Hmong",
            "Hungarian",
            "Icelandic",
            "Igbo",
            "Indonesian",
            "Irish",
            "Italian",
            "Japanese",
            "Javanese",
            "Kannada",
            "Kazakh",
            "Khmer",
            "Kinyarwanda",
            "Korean",
            "Kurdish",
            "Kyrgyz",
            "Lao",
            "Latvian",
            "Lithuanian",
            "Luxembourgish",
            "Macedonian",
            "Malagasy",
            "Malay",
            "Malayalam",
            "Maltese",
            "Maori",
            "Marathi",
            "Mongolian",
            "Myanmar (Burmese)",
            "Nepali",
            "Norwegian",
            "Nyanja (Chichewa)",
            "Odia (Oriya)",
            "Pashto",
            "Persian",
            "Polish",
            "Portuguese (Portugal, Brazil)",
            "Punjabi",
            "Romanian",
            "Russian",
            "Samoan",
            "Scots Gaelic",
            "Serbian",
            "Sesotho",
            "Shona",
            "Sindhi",
            "Sinhala (Sinhalese)",
            "Slovak",
            "Slovenian",
            "Somali",
            "Spanish",
            "Sundanese",
            "Swahili",
            "Swedish",
            "Tagalog (Filipino)",
            "Tajik",
            "Tamil",
            "Tatar",
            "Telugu",
            "Thai",
            "Turkish",
            "Turkmen",
            "Ukrainian",
            "Urdu",
            "Uyghur",
            "Uzbek",
            "Vietnamese",
            "Welsh",
            "Xhosa",
            "Yiddish",
            "Yoruba",
            "Zulu"
        )

        fun funGetLanguagesListOffline(): MutableList<ModelLanguage> {
            var model: ModelLanguage?
            val list: MutableList<ModelLanguage> = ArrayList()
            var i = 0;
            LANGUAGES_CODES.forEach {
                model = ModelLanguage(it, LANGUAGES_NAMES[i])
                list.add(model!!)
                i++
            }
            return list
        }

        fun funGetLanguagesListOnline(): MutableList<ModelLanguage> {
            var model: ModelLanguage?
            val list: MutableList<ModelLanguage> = ArrayList()
            var i = 0;
            LANGUAGES_CODES_ONLINE.forEach {
                model = ModelLanguage(it, LANGUAGES_NAMES_ONLINE[i])
                list.add(model!!)
                i++
            }
            return list
        }

        fun funGetLanguagesListCameraSupported(): MutableList<ModelLanguage> {
            var model: ModelLanguage?
            val list: MutableList<ModelLanguage> = ArrayList()
            var i = 0;
            LANGUAGES_CODES_CAMERA_SUPPORTED.forEach {
                model = ModelLanguage(it, LANGUAGES_NAMES_CAMERA_SUPPORTED[i])
                list.add(model!!)
                i++
            }
            return list
        }


    }

}

