package com.myapps.noteapp.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color.parseColor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.myapps.noteapp.activities.EditorActivity
import com.myapps.noteapp.model.NoteEntity
import com.myapps.noteapp.R

class RvNoteAdapter(context: Context, private var list: MutableList<NoteEntity>):
    RecyclerView.Adapter<RvNoteAdapter.ViewHolder>() {
    private  var listener: OnClickListener?=null
    var context1=context



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.note_rv_row_items,parent,false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: NoteEntity =list[position]
        holder.titleText.text=note.title
        holder.noteText.text=note.noteText
        holder.createTime.text=note.createTime
        holder.backgroundColor.setCardBackgroundColor(parseColor(note.background))

        holder.popupButton.setOnClickListener {
            if (holder.adapterPosition!=RecyclerView.NO_POSITION)
                listener?.OnClicked(holder.adapterPosition,holder.popupButton)


        }
        holder.itemView.setOnClickListener {}
        holder.bind(note)

    }

    override fun getItemCount(): Int {
       return list.size
    }
    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val titleText: TextView =itemView.findViewById<TextView>(R.id.text_title)
        val noteText: TextView =itemView.findViewById<TextView>(R.id.note_text)
        val createTime: TextView =itemView.findViewById<TextView>(R.id.created_data)
        val popupButton: ImageButton =itemView.findViewById<ImageButton>(R.id.popup_menu)
        var backgroundColor:CardView =itemView.findViewById<CardView>(R.id.note_card_holder)
          fun bind(note: NoteEntity){
            itemView.setOnClickListener {
                val intent=Intent(context1, EditorActivity::class.java)
                intent.putExtra("edit","show")
                intent.putExtra("title",note.title)
                intent.putExtra("text",note.noteText)
                intent.putExtra("time",note.createTime)
                intent.putExtra("background",note.background)
                intent.putExtra("id",note.id)
                intent.putExtra("index",note.tableNumber)
                intent.putExtra("favorite",note.favorite)
                intent.putExtra("archive",note.archive)
                context1.startActivity(intent)


            }
              

        }
    }


    fun setOnClickListener(listener: OnClickListener){
        this.listener=listener

    }
    interface OnClickListener{
        fun OnClicked(position: Int,view: View)
    }
}