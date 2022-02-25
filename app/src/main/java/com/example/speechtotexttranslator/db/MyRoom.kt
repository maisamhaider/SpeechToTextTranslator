package com.example.speechtotexttranslator.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.speechtotexttranslator.db.dictionary.DaoDictionaryFavorites
import com.example.speechtotexttranslator.db.dictionary.DaoDictionaryHistory
import com.example.speechtotexttranslator.db.offline.DaoTranslatedResult
import com.example.speechtotexttranslator.db.recentlanguages.DaoRecentLanguages
import com.example.speechtotexttranslator.db.speakandtranslate.DaoSpeakAndTranslateResult
import com.example.speechtotexttranslator.db.speechtotext.DaoSpeechToText
import com.example.speechtotexttranslator.db.usefullphrases.DaoUseFullPhrasesFavorites
import com.example.speechtotexttranslator.db.voicetranslator.DaoVoiceTranslatorFavorites
import com.example.speechtotexttranslator.db.voicetranslator.DaoVoiceTranslatorHistory
import com.example.speechtotexttranslator.models.*

@Database(
    entities = [ModelOfflineTranslatorResult::class,
        ModelSpeakAndTranslateResult::class, ModelVoiceTranslatorFavorites::class,
        ModelVoiceTranslatorHistory::class, ModelSpeechToTextNote::class,
        ModelUseFullPhrasesFavorites::class, ModelDictionaryHistory::class,
        ModelDictionaryFavorites::class, ModelRecentLanguages::class],
    version = 2,
    exportSchema = false
)
abstract class MyRoom : RoomDatabase() {
    abstract fun daoTranslatedResult(): DaoTranslatedResult?
    abstract fun daoTranslatedResultOnline(): DaoSpeakAndTranslateResult?
    abstract fun daoVoiceTranslatorFavorites(): DaoVoiceTranslatorFavorites?
    abstract fun daoVoiceTranslatorHistory(): DaoVoiceTranslatorHistory?
    abstract fun daoSpeechToText(): DaoSpeechToText?
    abstract fun daoUseFullPhrases(): DaoUseFullPhrasesFavorites?
    abstract fun daoDictionaryHistory(): DaoDictionaryHistory?
    abstract fun daoDictionaryFavorites(): DaoDictionaryFavorites?
    abstract fun daoRecentLanguages(): DaoRecentLanguages?

    companion object {
        @Volatile
        var INSTANCE: MyRoom? = null
        fun getInstance(context: Context): MyRoom {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, MyRoom::class.java,
                    "DATABASE_ROOM").fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }


}