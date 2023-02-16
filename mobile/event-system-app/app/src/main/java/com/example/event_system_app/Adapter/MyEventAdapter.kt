package com.example.event_system_app.Adapter

import android.content.Context
import android.text.TextUtils.split
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.event_system_app.Model.Event
import com.example.event_system_app.R

class MyEventAdapter(private val events: ArrayList<Event>, private val context: Context): RecyclerView.Adapter<MyEventAdapter.MyViewHolder>() {
    var onItemClick : ((Event) -> Unit) ?= null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){ //Инициализация объектов лайаута айтемов ресайклера
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val eventSmallImg: ImageView = itemView.findViewById(R.id.eventSmallImg)
        val tagText: TextView = itemView.findViewById(R.id.tagText)
        val dateText: TextView = itemView.findViewById(R.id.dateText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { //Подвязка лайаута к адаптеру ресайклера
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_my_events_recycler, parent, false) //Определение лайаута
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) { //Запись в айтем данных в заготовленные textview
        var title = split(events[position].title, 25)
        holder.titleText.text = title[0] + "..."
//        var title = events[position].title
//        holder.titleText.text = events[position].title
        holder.tagText.text = events[position].tags
        holder.dateText.text = events[position].date

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(events[position])
        }

        val imgUrl = events[position].imgUrl
        Glide.with(context)
            .load(imgUrl)
            .placeholder(R.drawable.events_icon)
            .into(holder.eventSmallImg);
    }

    private fun split(s: String, size: Int): List<String> {
        val chunks = mutableListOf<String>()
        var i = 0
        while (i < s.length) {
            chunks.add(s.substring(i, Math.min(s.length, i + size)))
            i += size
        }
        return chunks
    }

    override fun getItemCount(): Int {
        return events.size
    }
}