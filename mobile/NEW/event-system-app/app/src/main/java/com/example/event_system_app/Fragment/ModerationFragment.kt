package com.example.event_system_app.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.event_system_app.Activity.EventInfoActivity
import com.example.event_system_app.Activity.NetworkErrorActivity
import com.example.event_system_app.Adapter.MyEventAdapter
import com.example.event_system_app.Helper.ServerHelper
import com.example.event_system_app.Helper.SharedPrefs
import com.example.event_system_app.Model.Event
import com.example.event_system_app.R

class ModerationFragment: Fragment() {
    private lateinit var myEventsSearchView: SearchView
    private lateinit var serverHelper: ServerHelper

    private lateinit var progressBar: ProgressBar
    private lateinit var no_event_layout: LinearLayout
    private lateinit var event_layout: LinearLayout
    private lateinit var sharedPrefs: SharedPrefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_myevents, container, false)
        init(view)
        serverHelper = ServerHelper(requireContext())
        checkConnection()
        val eventList = ArrayList<Event>()
        val event1 = Event(
            5,
            "Поле чудес",
            "Очутиться в «телевизоре» своего детства, почувствовать настольгию и понять, что это не так-то просто. Мы часто сидели у экрана и думали: «Да как же можно этого не знать? Ну тут же слово вырисовывается элементарное! Да я бы там уже всё сто раз отгадал»",
            arrayOf("https://sun4-22.userapi.com/impg/ID3e-8pRzvt8YqnVCdlUndNUUo3UK7ZPG3bcDw/4-dCPYSWMl4.jpg?size=1647x2160&quality=95&sign=91c011b4b2c353efa49fafcfe0e014d9&type=album"),
            "Внешнее",
            "26.03.2023 19:05",
            "Точка кипения",
            null,
            null,
            null,
            null
        )
        eventList.add(event1)
        setMyEventAdapter(eventList, view, requireContext())
        checkEvent()
        //Слушатель поисковика
        myEventsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { //Поиск по карточкам

                val searchEventList = ArrayList<Event>()
                eventList.forEach {
                    if(it.title!!.contains(query!!, ignoreCase = true)){
                        searchEventList.add(it)
                    }
                }
                setMyEventAdapter(searchEventList, view, requireContext())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean { //Сброс поиска
                if(newText!!.isEmpty()){
                    setMyEventAdapter(eventList, view, requireContext())
                }
                return false
            }
        })

        return view
    }

    private fun checkEvent() {
        progressBar.visibility = View.VISIBLE
        val rnds = (1000..3000).random()
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val res = sharedPrefs.getLoginCount()
                if (res == 0){
                    no_event_layout.visibility = View.VISIBLE
                    event_layout.visibility = View.INVISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
                else{
                    no_event_layout.visibility = View.GONE
                    event_layout.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
            },
            rnds.toLong() // value in milliseconds
        )
    }

    private fun setMyEventAdapter(events: ArrayList<Event>, view: View, context: Context){ //Адаптер текущих игр
        val recyclerView: RecyclerView = view.findViewById(R.id.myEventsRecyclerView) //Подвязка ресайклера к объекту
        val linearLayoutManager = LinearLayoutManager(context) //Подготовка лайаут менеджера
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager //Инициализация лайаут менеджера
        val presenceAdapter = MyEventAdapter(events, context) //внесение данных из листа в адаптер (заполнение данными)
        recyclerView.adapter = presenceAdapter //внесение данных из листа в адаптер (заполнение данными)

        presenceAdapter.onItemClick = {

            val event1 = Event(
                5,
                "Поле чудес",
                "Очутиться в «телевизоре» своего детства, почувствовать настольгию и понять, что это не так-то просто. Мы часто сидели у экрана и думали: «Да как же можно этого не знать? Ну тут же слово вырисовывается элементарное! Да я бы там уже всё сто раз отгадал»",
                arrayOf("https://sun4-22.userapi.com/impg/ID3e-8pRzvt8YqnVCdlUndNUUo3UK7ZPG3bcDw/4-dCPYSWMl4.jpg?size=1647x2160&quality=95&sign=91c011b4b2c353efa49fafcfe0e014d9&type=album"),
                "Внешнее",
                "26.03.2023 19:05",
                "Точка кипения",
                70,
                "10.03.2023 00:01",
                "22.03.2023 23:55",
                "Иванов Иван\nКириченко Андрей\nПроскофья Смолина"
            )

        //    Toast.makeText(context, "Открою ивент ${myEvent.title}", Toast.LENGTH_SHORT).show()
            val i = Intent(context, EventInfoActivity::class.java)
            i.putExtra("event", event1)
            context.startActivity(i)
        }
    }

    //Инициализация компонентов
    private fun init(view: View) {
        myEventsSearchView = view.findViewById(R.id.myEventsSearchView)
        progressBar = view.findViewById(R.id.progressBar)
        sharedPrefs = SharedPrefs(view.context)
        no_event_layout = view.findViewById(R.id.no_event_layout)
        event_layout = view.findViewById(R.id.event_layout)
        progressBar.visibility = View.INVISIBLE
        event_layout.visibility = View.INVISIBLE
        no_event_layout.visibility = View.INVISIBLE
    }

    private fun checkConnection() {
        if(!serverHelper.isOnline(requireContext())){
            val i = Intent(requireContext(), NetworkErrorActivity::class.java)
            startActivity(i)
        }
    }
}