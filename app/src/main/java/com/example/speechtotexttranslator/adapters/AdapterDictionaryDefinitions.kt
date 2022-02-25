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
import com.example.speechtotexttranslator.models.Definition

class AdapterDictionaryDefinitions(val context: Context) :
    ListAdapter<Definition, AdapterDictionaryDefinitions.Holder>(MyDifUtil()) {

    class MyDifUtil : DiffUtil.ItemCallback<Definition>() {
        override fun areItemsTheSame(
            oldItem: Definition,
            newItem: Definition
        ): Boolean {
            return oldItem.definition == newItem.definition &&
                    oldItem.example == newItem.example &&
                    oldItem.synonyms == newItem.synonyms &&
                    oldItem.antonyms == newItem.antonyms
        }

        override fun areContentsTheSame(
            oldItem: Definition,
            newItem: Definition
        ): Boolean {
            return oldItem.definition == newItem.definition &&
                    oldItem.example == newItem.example &&
                    oldItem.synonyms == newItem.synonyms &&
                    oldItem.antonyms == newItem.antonyms
        }

    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewDefinition: TextView = itemView.findViewById(R.id.textViewDefinition)
        val textViewExample: TextView = itemView.findViewById(R.id.textViewExample)
        val recyclerViewSynonyms: RecyclerView = itemView.findViewById(R.id.recyclerViewSynonyms)
        val recyclerViewAntonyms: RecyclerView = itemView.findViewById(R.id.recyclerViewAntonyms)
        val textViewSynonyms: TextView = itemView.findViewById(R.id.textView2)
        val textViewAntonyms: TextView = itemView.findViewById(R.id.textView3)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_dictionary_definitions_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        if (item.definition.isNotBlank()) {
            holder.textViewDefinition.text = item.definition
        }
        if (item.example.isNotBlank()) {
            holder.textViewExample.text = item.example
        }
        if (item.synonyms.isEmpty()) {
            holder.textViewSynonyms.visibility = View.GONE

        } else {
            val adapter1 = AdapterDictionarySynoAnto(context)
            val llm1 = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            holder.recyclerViewSynonyms.layoutManager = llm1
            holder.recyclerViewSynonyms.adapter = adapter1
            adapter1.submitList(item.synonyms)
        }
        if (item.antonyms.isEmpty()) {
            holder.textViewAntonyms.visibility = View.GONE
        } else {
            val adapter2 = AdapterDictionarySynoAnto(context)
            val llm2 = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            holder.recyclerViewAntonyms.layoutManager = llm2
            holder.recyclerViewAntonyms.adapter = adapter2
            adapter2.submitList(item.antonyms)
        }


    }
}