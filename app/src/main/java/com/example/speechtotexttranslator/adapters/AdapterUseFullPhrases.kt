package com.example.speechtotexttranslator.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.USEFUL_PHRASES_LIST_NO
import com.example.speechtotexttranslator.ui.activities.usefullphrases.ActivityUseFullPhrasesResult
import com.example.speechtotexttranslator.utils.AppPreferences.funGetString
import com.example.speechtotexttranslator.utils.Singleton.toastLong

class AdapterUseFullPhrases(val context: Context) :
    ListAdapter<String, AdapterUseFullPhrases.Holder>(MyDiffUtil()) {


    class MyDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean {
            return oldItem == newItem

        }

    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSourceLang: TextView = itemView.findViewById(R.id.textViewSourceLang)
        val textViewSource: TextView = itemView.findViewById(R.id.textViewSource)
        val clMain: ConstraintLayout = itemView.findViewById(R.id.clMain)

    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_usefull_phrases_item,
            parent, false
        )

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val spokenText = getItem(position)
        val sourceName = context.funGetString(
            AnNot.ObjPreferencesKeys.SOURCE_LANGUAGE_SELECTED_NAME_USEFUL_PHRASES,
            "English"
        )

        holder.textViewSource.text = spokenText
        holder.textViewSourceLang.text = sourceName


        holder.clMain.setOnClickListener {
            context.toastLong("click")
            context.startActivity(
                Intent(context, ActivityUseFullPhrasesResult::class.java)
                    .putExtra(USEFUL_PHRASES_LIST_NO, position)
            )
        }

    }


}