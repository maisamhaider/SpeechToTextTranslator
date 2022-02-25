package com.example.speechtotexttranslator.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.annotations.AnNot.ObjIntentKeys.WORD
import com.example.speechtotexttranslator.ui.activities.dictionary.ActivityDictionary
import com.example.speechtotexttranslator.ui.activities.dictionary.ActivityDictionaryResult

class AdapterDictionaryHistory(val activity: Activity) :
    ListAdapter<String, AdapterDictionaryHistory.Holder>(MyDiffUtil()) {

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
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_name_and_forwerd_errow_item,
            parent, false
        )
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)

        holder.textViewTitle.text = item

        holder.itemView.setOnClickListener {
            activity.startActivity(Intent(activity, ActivityDictionaryResult::class.java).apply {
                putExtra(WORD, getItem(position))
            })
//            activity.finish()
        }
    }
}