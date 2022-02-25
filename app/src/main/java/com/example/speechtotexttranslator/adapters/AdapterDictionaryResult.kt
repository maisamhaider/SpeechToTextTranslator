package com.example.speechtotexttranslator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.models.DictionaryResponse

class AdapterDictionaryResult(val context: Context) :
    ListAdapter<DictionaryResponse, AdapterDictionaryResult.Holder>(MyDifUtil()) {

    class MyDifUtil : DiffUtil.ItemCallback<DictionaryResponse>() {
        override fun areItemsTheSame(
            oldItem: DictionaryResponse,
            newItem: DictionaryResponse
        ): Boolean {
            return oldItem.word == newItem.word &&
                    oldItem.phonetic == newItem.phonetic &&
                    oldItem.origin == newItem.origin &&
                    oldItem.phonetics == newItem.phonetics &&
                    oldItem.meanings == newItem.meanings
        }

        override fun areContentsTheSame(
            oldItem: DictionaryResponse,
            newItem: DictionaryResponse
        ): Boolean {
            return oldItem.word == newItem.word &&
                    oldItem.phonetic == newItem.phonetic &&
                    oldItem.origin == newItem.origin &&
                    oldItem.phonetics == newItem.phonetics &&
                    oldItem.meanings == newItem.meanings
        }

    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewWord: TextView = itemView.findViewById(R.id.textViewWord)
        val textViewPhonetic: TextView = itemView.findViewById(R.id.textViewPhonetic)
        val textViewOrigin: TextView = itemView.findViewById(R.id.textViewOrigin)
        val phonetic: TextView = itemView.findViewById(R.id.appCompatTextView4)
        val origin: TextView = itemView.findViewById(R.id.appCompat)
        val recyclerViewMeanings: RecyclerView = itemView.findViewById(R.id.recyclerViewMeanings)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_dictionary_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        if (item.word.isNotBlank()) {
            holder.textViewWord.text = item.word
        } else {

        }
        if (item.phonetic.isNotBlank()) {
            holder.textViewPhonetic.text = item.phonetic
        } else {
            holder.phonetic.visibility = View.GONE
        }
        if (item.origin.isNotBlank()) {
            holder.textViewOrigin.text = item.origin
        } else {
            holder.origin.visibility = View.GONE

        }
        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = AdapterDictionaryMeanings(context)
        holder.recyclerViewMeanings.layoutManager = llm
        holder.recyclerViewMeanings.adapter = adapter
        adapter.submitList(item.meanings)

    }
}