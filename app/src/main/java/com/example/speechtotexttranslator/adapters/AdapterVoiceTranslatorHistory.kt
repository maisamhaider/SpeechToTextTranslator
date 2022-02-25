package com.example.speechtotexttranslator.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorFavorites
import com.example.speechtotexttranslator.models.ModelVoiceTranslatorHistory
import com.example.speechtotexttranslator.ui.activities.voicetranslate.ActivityVoiceTranslatorResult
import com.example.speechtotexttranslator.utils.Singleton.toastLong

class AdapterVoiceTranslatorHistory(var context: Context) :
    ListAdapter<ModelVoiceTranslatorHistory,
            AdapterVoiceTranslatorHistory.Holder>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<ModelVoiceTranslatorHistory>() {
        override fun areItemsTheSame(
            oldItem: ModelVoiceTranslatorHistory,
            newItem: ModelVoiceTranslatorHistory
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ModelVoiceTranslatorHistory,
            newItem: ModelVoiceTranslatorHistory
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.sourcesLanguage == newItem.sourcesLanguage &&
                    oldItem.targetLanguage == newItem.sourcesText &&
                    oldItem.targetText == newItem.targetText;
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_name_and_forwerd_errow_item, parent, false
        )
        return Holder(view)
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.textViewTitle.text = getItem(position).sourcesText

        holder.itemView.setOnClickListener {
            context.startActivity(
                Intent(context, ActivityVoiceTranslatorResult::class.java)
                    .putExtra(
                        AnNot.ObjIntentKeys.TEXT_SOURCE, getItem(holder.adapterPosition)
                            .sourcesText
                    ).putExtra(
                        AnNot.ObjIntentKeys.TEXT_RESULT, getItem(holder.adapterPosition).targetText
                    ).putExtra(AnNot.ObjIntentKeys.IS_VOICE_TRANSLATOR_FAVORITE_RESULT, true)
                    .putExtra(
                        AnNot.ObjIntentKeys.SOURCE_LANGUAGE,
                        getItem(holder.adapterPosition).sourcesLanguage
                    )
                    .putExtra(
                        AnNot.ObjIntentKeys.TARGET_LANGUAGE,
                        getItem(holder.adapterPosition).targetLanguage
                    ).putExtra(
                        AnNot.ObjIntentKeys.CODE_SOURCE,
                        getItem(holder.adapterPosition).sourcesCode
                    ).putExtra(
                        AnNot.ObjIntentKeys.CODE_RESULT,
                        getItem(holder.adapterPosition).targetCode
                    )
            )
        }
    }
}


