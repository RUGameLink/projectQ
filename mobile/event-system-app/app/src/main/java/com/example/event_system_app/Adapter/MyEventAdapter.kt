package com.example.event_system_app.Adapter

import android.content.Context
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
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){ //Инициализация объектов лайаута айтемов ресайклера
        val titleText: TextView = itemView.findViewById(R.id.titleText)
        val eventSmallImg: ImageView = itemView.findViewById(R.id.eventSmallImg)
        val tagText: TextView = itemView.findViewById(R.id.tagText)
        val dateText: TextView = itemView.findViewById(R.id.dateText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { //Подвязка лайаута к адаптеру ресайклера
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.my_events_recycler_item, parent, false) //Определение лайаута
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) { //Запись в айтем данных в заготовленные textview

        holder.titleText.text = events[position].title
        holder.tagText.text = events[position].tags
        holder.dateText.text = events[position].date

        val imgUrl = events[position].imgUrl
        Glide.with(context)
            .load(imgUrl)
            .placeholder(R.drawable.events_icon)
            .into(holder.eventSmallImg);
    }

    override fun getItemCount(): Int {
        return events.size
    }
}