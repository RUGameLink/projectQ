package com.example.event_system_app.Helper

import com.example.event_system_app.Model.Event

class ServerHelper {
    public fun getAllEvents(): ArrayList<Event>{
        val eventList = ArrayList<Event>()
        val URL = "http://rating-teams-events.ovz1.j37315531.m1yvm.vps.myjino.ru/api/events/external"

        return eventList
    }
}