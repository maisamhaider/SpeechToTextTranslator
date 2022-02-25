package com.example.speechtotexttranslator.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.speechtotexttranslator.R
import com.example.speechtotexttranslator.annotations.AnNot
import com.example.speechtotexttranslator.models.ModelSpeechToTextNote
import com.example.speechtotexttranslator.ui.activities.speechtotext.ActivitySpeechToTextNoteView

class AdapterSpeechToTextNote(val context: Context) : ListAdapter<ModelSpeechToTextNote,
        AdapterSpeechToTextNote.Holder>(MyDiffUtil()) {

    class MyDiffUtil : DiffUtil.ItemCallback<ModelSpeechToTextNote>() {
        override fun areItemsTheSame(
            oldItem: ModelSpeechToTextNote,
            newItem: ModelSpeechToTextNote
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ModelSpeechToTextNote,
            newItem: ModelSpeechToTextNote
        ): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.noteTitle == newItem.noteTitle &&
                    oldItem.noteLanguage == newItem.noteLanguage &&
                    oldItem.noteDate == newItem.noteDate &&
                    oldItem.noteText == newItem.noteText
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNoteTitle = itemView.findViewById<TextView>(R.id.textViewNoteTitle)!!
        val textViewNoteDate = itemView.findViewById<TextView>(R.id.textViewNoteDate)!!
        val textViewNoteText = itemView.findViewById<TextView>(R.id.textViewNoteText)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_speech_to_text_note_item, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.textViewNoteTitle.text = item.noteTitle
        holder.textViewNoteDate.text = item.noteDate
        holder.textViewNoteText.text = item.noteText

        holder.itemView.setOnClickListener {
            context.startActivity(Intent(context, ActivitySpeechToTextNoteView::class.java).apply {
                putExtra(
                    AnNot.ObjIntentKeys.SPEECH_TO_TEXT_NOTE_ID, getItem(holder.adapterPosition)
                        .id
                )
                putExtra(
                    AnNot.ObjIntentKeys.SPEECH_TO_TEXT_NOTE_CODE, getItem(holder.adapterPosition)
                        .noteCode
                )
            })
        }
    }
}