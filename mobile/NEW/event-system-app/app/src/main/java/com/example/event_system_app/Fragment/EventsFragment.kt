package com.example.event_system_app.Fragment

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.event_system_app.Adapter.EventAdapter
import com.example.event_system_app.Model.Event
import com.example.event_system_app.R
import android.widget.SearchView
import com.example.event_system_app.Helper.ServerHelper
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup

class EventsFragment: Fragment() {
    private lateinit var eventsSearchView: SearchView
    private lateinit var tagsGrpup: SingleSelectToggleGroup
    private lateinit var serverHelper: ServerHelper

    private var tags: String = ""
    var eventList = ArrayList<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        getActivity()?.setRequestedOrientation(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        init(view)
        serverHelper = ServerHelper(requireContext())
        tagsGrpup.check(R.id.anyToggle)
        eventList = serverHelper.getExternalEvents()

        setEventAdapter(eventList, view, requireContext())

        eventsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { //Поиск в карточках
                val searchEventList = ArrayList<Event>()
                eventList.forEach {
                    if (!tags.equals("Любое")){
                        if(it.title!!.contains(query!!, ignoreCase = true) && it.tags!!.contains(tags!!, ignoreCase = true)){
                            searchEventList.add(it)
                        }
                    }
                    else{
                        if(it.title!!.contains(query!!, ignoreCase = true)){
                            searchEventList.add(it)
                        }
                    }

                }
                setEventAdapter(searchEventList, view, requireContext())
                return false
            }

            //Сброс поиска
            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!!.isEmpty()){
                    setEventAdapter(eventList, view, requireContext())
                    tagsGrpup.check(R.id.anyToggle)
                }
                return false
            }
        })

        //Слушатель кнопок фильтра
        tagsGrpup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.internallToggle -> {
                    tags = "Внутреннее"
                    searchTag(view)
                }
                R.id.externalToggle -> {
                    tags = "Внешнее"
                    searchTag(view)
                }
                R.id.anyToggle -> {
                    tags = "Любое"
                    searchTag(view)
                }
            }
        }

        return view
    }

    //Поиск по тегу
    private fun searchTag(view: View){
        if(tags.equals("Любое")){
            println("Размер листа ивентов ${eventList.size}")
            context?.let { setEventAdapter(eventList, view, it) }
        }
        else {
            val searchEventList = ArrayList<Event>()
            eventList.forEach {
                if (it.tags!!.contains(tags!!, ignoreCase = true)) {
                    searchEventList.add(it)
                }

                setEventAdapter(searchEventList, view, requireContext())
            }
        }
    }

    private fun setEventAdapter(events: ArrayList<Event>, view: View, context: Context){ //Адаптер текущих игр
        val recyclerView: RecyclerView = view.findViewById(R.id.eventsRecyclerView) //Подвязка ресайклера к объекту
        val linearLayoutManager = LinearLayoutManager(context) //Подготовка лайаут менеджера
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager //Инициализация лайаут менеджера
        recyclerView.adapter = EventAdapter(events, context) //внесение данных из листа в адаптер (заполнение данными)
    }

    //Инициализация компонентов
    private fun init(view: View) {
        eventsSearchView = view.findViewById(R.id.eventsSearchView)
        tagsGrpup = view.findViewById(R.id.tags_group)
    }
}