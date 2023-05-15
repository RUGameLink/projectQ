package com.example.event_system_app.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.event_system_app.Activity.EventActivity
import com.example.event_system_app.Model.Event
import com.example.event_system_app.R
import com.google.android.material.button.MaterialButton

class EventResponsibleAdapter(private val events: ArrayList<Event>, private val context: Context): RecyclerView.Adapter<EventResponsibleAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){ //Инициализация объектов лайаута айтемов ресайклера
        val titleEventText: TextView = itemView.findViewById(R.id.titleEventText)
        val eventImg: ImageView = itemView.findViewById(R.id.eventImg)
        val tagsEventText: TextView = itemView.findViewById(R.id.tagsEventText)
        val dateEventText: TextView = itemView.findViewById(R.id.dateEventText)
        val descEventText: TextView = itemView.findViewById(R.id.descEventText)

        val descButton: MaterialButton = itemView.findViewById(R.id.descButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { //Подвязка лайаута к адаптеру ресайклера
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_events_resbonsible_recycler, parent, false) //Определение лайаута
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventResponsibleAdapter.MyViewHolder, position: Int) {
        holder.titleEventText.text = events[position].title
        holder.tagsEventText.text = events[position].tags
        holder.dateEventText.text = events[position].date
        holder.descEventText.text = events[position].description

        val imgUrl = events[position].imgUrl!![0]
        Glide.with(context)
            .load(imgUrl)
            .placeholder(R.drawable.icon_events)
            .into(holder.eventImg);

        holder.descButton.setOnClickListener {
            val i = Intent(context, EventActivity::class.java)
            val event = events[position]
            i.putExtra("event", event)
            context.startActivity(i)
        }
    }
}