package com.example.speechtotexttranslator.db.recentlanguages

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.speechtotexttranslator.models.ModelRecentLanguages

@Dao
interface DaoRecentLanguages {
    @Insert
    fun insert(modelRecentLanguages: ModelRecentLanguages)

    @Update
    fun update(modelRecentLanguages: ModelRecentLanguages)

    @Delete
    fun delete(modelRecentLanguages: ModelRecentLanguages)

    @Query("DELETE FROM TABLE_RECENT_LANGUAGES WHERE id = :id")
    fun deleteRL(id: Int)

    @Query("DELETE FROM TABLE_RECENT_LANGUAGES")
    fun deleteAllRL()

    @Query("SELECT * FROM TABLE_RECENT_LANGUAGES")
    fun getAllRL(): LiveData<List<ModelRecentLanguages>>

    @Query("SELECT * FROM TABLE_RECENT_LANGUAGES ORDER BY id ASC LIMIT 1")
    fun getFirstRow(): ModelRecentLanguages

    @Query("SELECT * FROM TABLE_RECENT_LANGUAGES WHERE code = :code AND name =:name")
    fun isLangExists(code: String, name: String): Int

    @Query("SELECT COUNT() FROM TABLE_RECENT_LANGUAGES")
    fun entriesCount(): Int
}