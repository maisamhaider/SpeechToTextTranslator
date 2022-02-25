package com.example.speechtotexttranslator.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.models.ModelOfflineTranslatorResult
import android.widget.*
import com.example.speechtotexttranslator.db.offline.ViewModelOfflineTranslatorResult
import com.example.speechtotexttranslator.utils.Singleton.funCopy
import com.example.speechtotexttranslator.utils.Singleton.funShare
import com.example.speechtotexttranslator.utils.Singleton.funTextToSpeech


class AdapterOfflineTranslateResult(
    private val context: Context,
    private val viewModel: ViewModelOfflineTranslatorResult,
) : ListAdapter<ModelOfflineTranslatorResult, AdapterOfflineTranslateResult.Holder>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<ModelOfflineTranslatorResult>() {
        override fun areItemsTheSame(
            oldItem: ModelOfflineTranslatorResult,
            newItem: ModelOfflineTranslatorResult
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ModelOfflineTranslatorResult,
            newItem: ModelOfflineTranslatorResult
        ): Boolean {
            return oldItem.targetLanguage == newItem.targetLanguage &&
                    oldItem.sourcesText == newItem.sourcesText &&
                    oldItem.targetText == newItem.targetText
        }

    }


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSource: TextView = itemView.findViewById(R.id.textViewSource)
        val textViewTarget: TextView = itemView.findViewById(R.id.textViewTarget)
        val textViewSourceLang: TextView = itemView.findViewById(R.id.textViewSourceLang)
        val textViewTargetLang: TextView = itemView.findViewById(R.id.textViewTargetLang)


        val imageButtonSpeak2: ImageButton = itemView.findViewById(R.id.imageButtonSpeak2)
        val imageButtonCopy2: ImageButton = itemView.findViewById(R.id.imageButtonCopy2)
        val imageButtonShare2: ImageButton = itemView.findViewById(R.id.imageButtonShare2)
        val imageButtonDelete2: ImageButton = itemView.findViewById(R.id.imageButtonDelete2)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_translate_result_item, parent,
            false
        )

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.textViewSource.text = item.sourcesText
        holder.textViewTarget.text = item.targetText

        holder.textViewSourceLang.text = item.sourcesLanguage
        holder.textViewTargetLang.text = item.targetLanguage



        holder.imageButtonSpeak2.setOnClickListener {
            if (item.targetText.toString().isBlank()) {
            } else {
                context.funTextToSpeech(item.targetText.toString(),getItem(position).targetCode.toString())
            }
        }
        holder.imageButtonCopy2.setOnClickListener {
            context.funCopy(getItem(holder.adapterPosition).targetText.toString())
        }
        holder.imageButtonShare2.setOnClickListener {
            context.funShare(getItem(holder.adapterPosition).targetText.toString())
        }
        holder.imageButtonDelete2.setOnClickListener {
            val po = holder.adapterPosition
            val id = getItem(po).id
            viewModel.funDelete(id)
            notifyItemChanged(po)

        }
    }


}