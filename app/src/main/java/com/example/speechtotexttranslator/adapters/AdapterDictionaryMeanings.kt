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
import com.example.speechtotexttranslator.models.Meaning

class AdapterDictionaryMeanings(val context: Context) :
    ListAdapter<Meaning, AdapterDictionaryMeanings.Holder>(MyDifUtil()) {

    class MyDifUtil : DiffUtil.ItemCallback<Meaning>() {
        override fun areItemsTheSame(
            oldItem: Meaning,
            newItem: Meaning
        ): Boolean {
            return oldItem.partOfSpeech == newItem.partOfSpeech &&
                    oldItem.definitions == newItem.definitions
        }

        override fun areContentsTheSame(
            oldItem: Meaning,
            newItem: Meaning
        ): Boolean {
            return oldItem.partOfSpeech == newItem.partOfSpeech &&
                    oldItem.definitions == newItem.definitions
        }

    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewAdjective: TextView = itemView.findViewById(R.id.textViewAdjective)
        val recyclerView: RecyclerView = itemView.findViewById(R.id.recyclerView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_dictionary_meaning_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        if (item.partOfSpeech.isNotBlank()) {
            holder.textViewAdjective.text = item.partOfSpeech
        }

        val adapter = AdapterDictionaryDefinitions(context)
        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        holder.recyclerView.layoutManager = llm
        holder.recyclerView.adapter = adapter
        adapter.submitList(item.definitions)

    }
}