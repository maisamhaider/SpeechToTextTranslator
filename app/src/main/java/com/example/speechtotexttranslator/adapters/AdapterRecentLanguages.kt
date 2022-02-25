package com.example.speechtotexttranslator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.db.recentlanguages.ViewModelResultLanguages
import com.example.speechtotexttranslator.interfeces.CallBack
import com.example.speechtotexttranslator.models.ModelLanguage
import com.example.speechtotexttranslator.models.ModelRecentLanguages
import com.example.speechtotexttranslator.utils.AppPreferences.funAddString

class AdapterRecentLanguages(
    var context: Context,
    private val viewModel: ViewModelResultLanguages,
    var selectedRecentLanguage: String,
    var callBack: CallBack,
    private var recentLanguageKey: String?,
    private var languageCodeKey: String?,
    private var languageNameKey: String?
) : ListAdapter<ModelRecentLanguages, AdapterRecentLanguages.Holder>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<ModelRecentLanguages>() {
        override fun areItemsTheSame(
            oldItem: ModelRecentLanguages,
            newItem: ModelRecentLanguages
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ModelRecentLanguages,
            newItem: ModelRecentLanguages
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.code == newItem.code
                    && oldItem.name == newItem.name
        }

    }


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewLanguageName: TextView = itemView.findViewById(R.id.textViewLanguageName)
        val checkbox: CheckBox = itemView.findViewById(R.id.checkbox)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_languages_item, parent,
            false
        )
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.textViewLanguageName.text = item.name
        holder.checkbox.isChecked = item.name.equals(selectedRecentLanguage)

        holder.itemView.setOnClickListener {
//            val code = item[position].code.toString()
//            val name = item[position].toString()
            context.funAddString(recentLanguageKey!!, getItem(position).name.toString())

            context.funAddString(languageCodeKey!!, getItem(position).code.toString())
            context.funAddString(languageNameKey!!, getItem(position).name.toString())
            callBack.call(call = true)


        }
    }

}
