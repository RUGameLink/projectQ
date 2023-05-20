package com.example.event_system_app.Activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.event_system_app.Model.Participant
import com.example.event_system_app.R
import com.google.android.material.appbar.MaterialToolbar
import ir.androidexception.datatable.DataTable
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow

class EventStatsActivity : AppCompatActivity() {
    private lateinit var eventStats: DataTable
    private val participantList = ArrayList<Participant>()
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_stats)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        init()

        val eventName = intent.getStringExtra("eventName")
        val eventId = intent.getStringExtra("eventId")!!.toLong()

        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = eventName

        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_back)
        toolbar.navigationIcon = getDrawable(R.drawable.icon_back)

        toolbar.setNavigationOnClickListener {
            val i = Intent(this, EventInfoActivity::class.java)
            startActivity(i)
            true
        }

        val participant1 = Participant(eventId, "Иванов Иван Иванович", "ИМп-12-2", "Да", "участник", "Иванов А.И")
        val participant2 = Participant(eventId, "Иванов Иван Иванович", "ИМп-12-2", "Да", "участник", "Иванов А.И")
        val participant3 = Participant(eventId, "Иванов Иван Иванович", "ИМп-12-2", "Да", "участник", "Иванов А.И")
        participantList.add(participant1)
        participantList.add(participant3)
        participantList.add(participant2)
        participantList.add(participant2)
        participantList.add(participant2)
        participantList.add(participant2)

        setTable()
    }

    private fun setTable() {
        var header = DataTableHeader.Builder()
            .item(getString(R.string.table_num_text), 10)
            .item(getString(R.string.table_name_text), 30)
            .item(getString(R.string.table_group_text), 10)
            .item(getString(R.string.table_role_text), 10)
            .item(getString(R.string.table_pres_presence_noted_text), 20)
            .item(getString(R.string.table_pres_text), 20)
            .build()

        val rows: ArrayList<DataTableRow> = ArrayList()
        var i = 1
        participantList.forEach {
            val row = DataTableRow.Builder()
                .value(i.toString())
                .value(it.userName)
                .value(it.studentGroup)
                .value(it.role)
                .value(it.presenceNoted)
                .value(it.presence)
                .build()

            rows.add(row)
            i ++
        }

        eventStats.headerBackgroundColor = R.color.green_color
        eventStats.header = header;
        eventStats.rows = rows;
        eventStats.inflate(this);
    }

    private fun init(){
        eventStats = findViewById(R.id.eventStats)
        toolbar = findViewById(R.id.toolbar)
    }
}