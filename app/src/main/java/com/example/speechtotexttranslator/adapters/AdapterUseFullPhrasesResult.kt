package com.example.speechtotexttranslator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.db.usefullphrases.ViewModelUseFullPhrasesFavorites
import com.example.speechtotexttranslator.models.ModelUseFullPhrasesFavorites
import com.example.speechtotexttranslator.models.ModelUseFullPhrasesResult
import com.example.speechtotexttranslator.utils.AppPreferences.funGetString
import com.example.speechtotexttranslator.utils.Singleton.funCopy
import com.example.speechtotexttranslator.utils.Singleton.funShare
import com.example.speechtotexttranslator.utils.Singleton.funTextToSpeech
import com.example.speechtotexttranslator.utils.Singleton.toastLong
import kotlinx.coroutines.runBlocking

class AdapterUseFullPhrasesResult(
    val context: Context,
    private val viewModelUseFullPhrasesFavorites: ViewModelUseFullPhrasesFavorites,
) : ListAdapter<ModelUseFullPhrasesResult, AdapterUseFullPhrasesResult.Holder>(MyDiffUtil()) {


    class MyDiffUtil : DiffUtil.ItemCallback<ModelUseFullPhrasesResult>() {
        override fun areItemsTheSame(
            oldItem: ModelUseFullPhrasesResult,
            newItem: ModelUseFullPhrasesResult,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ModelUseFullPhrasesResult,
            newItem: ModelUseFullPhrasesResult,
        ): Boolean {
            return oldItem.source == newItem.source &&
                    oldItem.target == newItem.target

        }

    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSourceLang: TextView = itemView.findViewById(R.id.textViewSourceLang)
        val textViewTargetLang: TextView = itemView.findViewById(R.id.textViewTargetLang)

        val textViewSource: TextView = itemView.findViewById(R.id.textViewSource)
        val textViewTarget: TextView = itemView.findViewById(R.id.textViewTarget)

        val textViewVisible: TextView = itemView.findViewById(R.id.textViewVisible)
        val scrollView3: ScrollView = itemView.findViewById(R.id.scrollView3)

        val imageButtonSpeak2: ImageButton = itemView.findViewById(R.id.imageButtonSpeak2)
        val imageButtonCopy2: ImageButton = itemView.findViewById(R.id.imageButtonCopy2)
        val imageButtonShare2: ImageButton = itemView.findViewById(R.id.imageButtonShare2)
        val imageFavorite: ImageButton = itemView.findViewById(R.id.imageFavorite)
    }

    private lateinit var holder: Holder
    var list: MutableList<String> = mutableListOf()
    private var visiableList: MutableList<String> = mutableListOf()
    private val sourceName = context.funGetString(
        AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES,
        "English"
    )
    private val targetName = context.funGetString(
        AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES,
        "English"
    )

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_usefull_phrases_result_item,
            parent, false
        )
        holder = Holder(view)
        return holder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        try {
            val item = getItem(holder.adapterPosition)


            if (visiableList.contains(list[holder.adapterPosition])) {
                holder.scrollView3.visibility = View.VISIBLE
            } else {
                holder.scrollView3.visibility = View.GONE
            }

            holder.textViewSource.text = item.source
            holder.textViewTarget.text = list[position]

            holder.textViewSourceLang.text = sourceName
            holder.textViewTargetLang.text = targetName


            holder.textViewVisible.setOnClickListener {
                if (visiableList.contains(list[position])) {
                    holder.scrollView3.visibility = View.GONE
                    visiableList.remove(list[position])
                } else {
                    visiableList.add(list[position])
                    holder.scrollView3.visibility = View.VISIBLE
                }
            }
            holder.imageButtonSpeak2.setOnClickListener {

                context.funTextToSpeech(list[position], getItem(position).targetCode)

            }
            holder.imageButtonCopy2.setOnClickListener {
                context.funCopy(list[position])
                context.toastLong("copied")
            }
            holder.imageButtonShare2.setOnClickListener {
                context.funShare(list[position])
            }
            holder.imageFavorite.setOnClickListener {
                val source = context.funGetString(
                    AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES,
                    "en"
                )
                val target = context.funGetString(
                    AnNot.ObjPreferencesKeys.TARGET_LANGUAGE_SELECTED_CODE_USEFUL_PHRASES,
                    "en"
                )
                runBlocking {
                    val liveData = viewModelUseFullPhrasesFavorites.isUseFullPhraseExists(
                        sourceText = getItem(position).source,
                        targetText = list[position]
                    )
                    if (liveData > 0) {
                        viewModelUseFullPhrasesFavorites.funDelete(item.source, list[position])
                        context.toastLong("deleted")
                    } else {
                        val model = ModelUseFullPhrasesFavorites(
                            source,
                            target,
                            sourceName,
                            targetName,
                            item.source,
                            list[position]
                        )
                        viewModelUseFullPhrasesFavorites.funInsert(model)
                        context.toastLong("Inserted")
                    }
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }

    }


}