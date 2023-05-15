package com.example.event_system_app.Helper

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.event_system_app.Model.Event
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


class ServerHelper(context: Context) {
    private val queue = Volley.newRequestQueue(context)
    private val EXTERNAL_URL = "http://rating-teams-events.ovz1.j37315531.m1yvm.vps.myjino.ru/api/events/external"

    fun getExternalEvents(): ArrayList<Event>{
        val eventList = ArrayList<Event>()

        val stringRequest = StringRequest(Request.Method.GET, EXTERNAL_URL, { //Передача запроса и получение ответа
                response -> //Случай удачного результата отклика api
            val obj = JSONArray(response) //Получение json файла
            print("check response ${obj}")
        //    val res = obj.getJSONObject("current") //Работа с заголовком current json
            for (i in 0 until obj.length()) {
                try {


                var images = obj.getJSONObject(i).optJSONArray("images").getString(0)
                val id = obj.getJSONObject(i).getString("id").toString().toLong()
                val type = obj.getJSONObject(i).getString("type")
                val title = obj.getJSONObject(i).getString("title")
                val dateStart = obj.getJSONObject(i).getString("dateStart")
                val description = obj.getJSONObject(i).getString("description")

                val res = images.toString()
            //    print("test obj: ${images}")
            //    var im = images.drop(2).dropLast(2)
            //    val url = URL(im.toString()).toString()
                println("\nsay me image: ${images}\n")
                eventList.add(Event(id, title, description, arrayOf(res), type, dateStart, null, null, null, null, null))
                }
                catch (ex: java.lang.NullPointerException){
                    println(ex)
                }
            }

        }, {
                error -> //Случай неудачного результата отклика api
            println("resp error ${error.toString()}")
        })
        queue.add(stringRequest) //Добавление запроса в очередь

        return eventList
    }
}