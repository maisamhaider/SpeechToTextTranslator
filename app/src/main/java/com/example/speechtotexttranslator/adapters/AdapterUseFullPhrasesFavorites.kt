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
import com.example.speechtotexttranslator.db.usefullphrases.ViewModelUseFullPhrasesFavorites
import com.example.speechtotexttranslator.models.ModelUseFullPhrasesFavorites
import com.example.speechtotexttranslator.utils.Singleton.funCopy
import com.example.speechtotexttranslator.utils.Singleton.funShare
import com.example.speechtotexttranslator.utils.Singleton.funTextToSpeech
import com.example.speechtotexttranslator.utils.Singleton.toastLong

class AdapterUseFullPhrasesFavorites(
    val context: Context,
    private val viewModelUseFullPhrasesFavorites: ViewModelUseFullPhrasesFavorites
) : ListAdapter<ModelUseFullPhrasesFavorites, AdapterUseFullPhrasesFavorites.Holder>(MyDiffUtil()) {


    class MyDiffUtil : DiffUtil.ItemCallback<ModelUseFullPhrasesFavorites>() {
        override fun areItemsTheSame(
            oldItem: ModelUseFullPhrasesFavorites,
            newItem: ModelUseFullPhrasesFavorites
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ModelUseFullPhrasesFavorites,
            newItem: ModelUseFullPhrasesFavorites
        ): Boolean {
            return oldItem.sourceLang == newItem.sourceLang &&
                    oldItem.targetLang == newItem.targetLang &&
                    oldItem.targetText == newItem.targetText &&
                    oldItem.sourceText == newItem.sourceText

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
    private var visiableList: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): Holder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_usefull_phrases_result_item,
            parent, false
        )
        holder = Holder(view)
        return holder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)

        if (visiableList.contains(item.id)) {
            holder.scrollView3.visibility = View.VISIBLE
        } else {
            holder.scrollView3.visibility = View.GONE

        }

        holder.textViewSource.text = item.sourceText
        holder.textViewTarget.text = item.targetText

        holder.textViewSourceLang.text = item.sourceLang
        holder.textViewTargetLang.text = item.targetLang


        holder.textViewVisible.setOnClickListener {
            if (visiableList.contains(getItem(holder.adapterPosition).id)) {
                holder.scrollView3.visibility = View.GONE
                visiableList.remove(getItem(holder.adapterPosition).id)
            } else {
                visiableList.add(getItem(holder.adapterPosition).id)
                holder.scrollView3.visibility = View.VISIBLE
            }
        }
        holder.imageButtonSpeak2.setOnClickListener {
            if (getItem(position).targetText.toString().isBlank()) {
            } else {
                context.funTextToSpeech(
                    getItem(position).targetText.toString(),
                    getItem(position).targetCode.toString()
                )
            }
        }
        holder.imageButtonCopy2.setOnClickListener {
            context.funCopy(getItem(holder.adapterPosition).targetText!!)
            context.toastLong("copied")
        }
        holder.imageButtonShare2.setOnClickListener {
            context.funShare(getItem(holder.adapterPosition).targetText!!)
        }
        holder.imageFavorite.setOnClickListener {
            viewModelUseFullPhrasesFavorites.funDelete(getItem(holder.adapterPosition).id)

            if (currentList.size == 1) {
                holder.itemView.visibility = View.GONE

            } else {
                notifyItemRemoved(holder.adapterPosition)
            }
            context.toastLong("removed")

        }

    }


}