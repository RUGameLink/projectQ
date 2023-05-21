package com.example.event_system_app.Helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.event_system_app.Model.Event
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat


class ServerHelper(context: Context) {
    private val queue = Volley.newRequestQueue(context)
    private val EXTERNAL_URL = "http://rating-teams-events.ovz1.j37315531.m1yvm.vps.myjino.ru/api/events/external"
    private val EVENT_URL = "http://rating-teams-events.ovz1.j37315531.m1yvm.vps.myjino.ru/api/events/external/"

    private var event = Event(null, null, null, null, null, null, null, null, null, null, null)

    fun getExternalEvents(): ArrayList<Event>{
        val eventList = ArrayList<Event>()

        val stringRequest = StringRequest(Request.Method.GET, EXTERNAL_URL, { //Передача запроса и получение ответа
                response -> //Случай удачного результата отклика api
            val obj = JSONArray(response) //Получение json файла
            print("check response ${obj}")
        //    val res = obj.getJSONObject("current") //Работа с заголовком current json
            for (i in 0 until obj.length()) {
                try {


                var images = obj.optJSONObject(i).optJSONArray("images").getString(0)
                val id = obj.optJSONObject(i).optString("id").toString().toLong()
                val type = obj.optJSONObject(i).optString("type")
                val title = obj.optJSONObject(i).optString("title")
                val dateStart = dateParsing(obj.optJSONObject(i).optString("dateStart"))
                val description = obj.optJSONObject(i).optString("description")

                val res = images.toString()

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

    fun getEventInfo(id: String){
            val stringRequest = StringRequest(Request.Method.GET, EVENT_URL + "$id", { //Передача запроса и получение ответа
                response -> //Случай удачного результата отклика api
            val obj = JSONObject(response) //Получение json файла
            print("check response ${obj}")
                try {
                    var images = obj.optJSONArray("images").getString(0)
                    val id = obj.optString("id").toString().toLong()
                    val type = obj.optString("type")
                    val title = obj.optString("title")
                    val dateStart = dateParsing(obj.optString("dateStart"))
                    val description = obj.optString("description")
                    val location = obj.optString("location")
                    val humanCount = obj.optInt("count_people")
                    val res = images.toString()

                    println("\nsay me image: ${images}\n")

                    event =  Event(id, title, description, arrayOf(res), type, dateStart, location, humanCount, null, null, null)
                }
                catch (ex: java.lang.NullPointerException){
                    println(ex)
                }
        }, {
                error -> //Случай неудачного результата отклика api
            println("resp error ${error}")
        })
        queue.add(stringRequest) //Добавление запроса в очередь
    }

    fun getEvent(): Event{
        return event
    }

    private fun dateParsing(date: String): String {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")


        return formatter.format(parser.parse(date))
    }

    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    fun cancellationRegistration() {
        TODO("Not yet implemented")
    }

    fun regForEvent(id: Long, id1: Long?) {

    }

    fun confirmPresence(id: Long, id1: Long?): Boolean {
        return true
    }
}