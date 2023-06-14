package com.example.event_system_app.Adapter

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.event_system_app.Activity.EventActivity
import com.example.event_system_app.Helper.SharedPrefs
import com.example.event_system_app.Model.Event
import com.example.event_system_app.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.itextpdf.text.factories.GreekAlphabetFactory.getString

class EventAdapter(private val events: ArrayList<Event>, private val context: Context): RecyclerView.Adapter<EventAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){ //Инициализация объектов лайаута айтемов ресайклера
        val titleEventText: TextView = itemView.findViewById(R.id.titleEventText)
        val eventImg: ImageView = itemView.findViewById(R.id.eventImg)
        val tagsEventText: TextView = itemView.findViewById(R.id.tagsEventText)
        val dateEventText: TextView = itemView.findViewById(R.id.dateEventText)
        val descEventText: TextView = itemView.findViewById(R.id.descEventText)

        val descButton: MaterialButton = itemView.findViewById(R.id.descButton)
        val runButton: MaterialButton = itemView.findViewById(R.id.runButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder { //Подвязка лайаута к адаптеру ресайклера
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_events_recycler, parent, false) //Определение лайаута
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) { //Запись в айтем данных в заготовленные textview

        holder.titleEventText.text = events[position].title
        holder.tagsEventText.text = events[position].tags
        holder.dateEventText.text = events[position].date
        holder.descEventText.text = events[position].description

        val imgUrl = events[position].imgUrl!![0]
        Handler().postDelayed({
            Glide.with(context)
                .load(imgUrl)
                .placeholder(R.drawable.icon_events)
                .into(holder.eventImg);
        }, 2000)


        holder.descButton.setOnClickListener {
            val i = Intent(context, EventActivity::class.java)
            val event = events[position]
            i.putExtra("eventId", event.id.toString())
            context.startActivity(i)
        }

        holder.runButton.setOnClickListener {
             var myPreference: SharedPrefs = SharedPrefs(context)
            val res = myPreference.getLoginCount()
            if (res == 0){
                Toast.makeText(context, context.getString(R.string.clean_profile_text), Toast.LENGTH_SHORT).show()
            }
            else{
                showTranslateDialog()

            }
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    private fun showTranslateDialog() {
        val builder = MaterialAlertDialogBuilder(context, R.style.MaterialAlertDialog_Rounded)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_status, null)
        builder.setView(dialogView)

        val russianRadioButton = dialogView.findViewById<RadioButton>(R.id.rus_rbtn)
        val englishRadioButton = dialogView.findViewById<RadioButton>(R.id.eng_rbtn)

        russianRadioButton.setOnClickListener {
            englishRadioButton.isChecked = false
            russianRadioButton.isChecked = true
            Toast.makeText(context, "Вы записаны в качестве участника", Toast.LENGTH_SHORT).show()

        }

        englishRadioButton.setOnClickListener {
            russianRadioButton.isChecked = false
            englishRadioButton.isChecked = true
            Toast.makeText(context, "Вы записаны в качестве зрителя", Toast.LENGTH_SHORT).show()
        }

        val dialog = builder.create()
        dialog.show()
    }
}