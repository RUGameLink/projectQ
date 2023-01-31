package com.example.event_system_app.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.event_system_app.Model.Event
import com.example.event_system_app.R

class EventAdapter(private val events: ArrayList<Event>, val context: Context): RecyclerView.Adapter<EventAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){ //Инициализация объектов лайаута айтемов ресайклера
        val titleEventText: TextView = itemView.findViewById(R.id.titleEventText)
        val eventImg: ImageView = itemView.findViewById(R.id.eventImg)
        val tagsEventText: TextView = itemView.findViewById(R.id.tagsEventText)
        val dateEventText: TextView = itemView.findViewById(R.id.dateEventText)
        val descEventText: TextView = itemView.findViewById(R.id.descEventText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { //Подвязка лайаута к адаптеру ресайклера
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.events_recycler_item, parent, false) //Определение лайаута
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) { //Запись в айтем данных в заготовленные textview

        holder.titleEventText.text = events[position].title
        holder.tagsEventText.text = events[position].tags
        holder.dateEventText.text = events[position].date
        holder.descEventText.text = events[position].description

        val imgUrl = events[position].imgUrl
        Glide.with(context)
            .load(imgUrl)
            .placeholder(R.drawable.events_icon)
            .into(holder.eventImg);
    }

    override fun getItemCount(): Int {
        return events.size
    }
}