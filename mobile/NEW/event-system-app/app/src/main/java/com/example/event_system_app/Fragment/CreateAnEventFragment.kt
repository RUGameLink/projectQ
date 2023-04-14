package com.example.event_system_app.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.event_system_app.Activity.EventInfoActivity
import com.example.event_system_app.Adapter.MyEventAdapter
import com.example.event_system_app.Model.Event
import com.example.event_system_app.Model.MyEvent
import com.example.event_system_app.R
import com.google.android.material.button.MaterialButton

class CreateAnEventFragment: Fragment() {
    private lateinit var myEventsSearchView: SearchView

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

        val eventList = ArrayList<Event>()
        val event1 = Event(
            1,
            "Встреча сети Точек кипения: 2023 год",
            "1 февраля в 10:00 мск приглашаем команды Точек кипения на общую встречу Сети.\n" +
                    "\n" +
                    "Обсудим:\n" +
                    "\n" +
                    "▫️Ключевые события 2023 года\n" +
                    "\n" +
                    "▫️Апрельский съезд\n" +
                    "\n" +
                    "▫️Архипелаг 2023\n" +
                    "\n" +
                    "▫️Технопром\n" +
                    "\n" +
                    "▫️Баркемп 2023\n" +
                    "\n" +
                    "А также, ключевые темы года.",
            (arrayOf("https://leader-id.storage.yandexcloud.net/upload/436356/7cc889c7-b2da-4077-883a-36b9111c930f.jpeg")),
            "Общественное",
            "02.03.2023 15:30",
            "Онлайн при поддержке Точка кипения - Москва",
            12
        )
        eventList.add(event1)
        val event2 = Event(
            2,
            "«Маркетинг и обновление бренда «Теремок»: реальные практики и работающие технологии»",
            "31 января 2023 в 17:30 (МСК) на базе Точке кипения РЭУ им. Г.В. Плеханова пройдет лекция для студентов и молодых предпринимателей совместно с Общероссийской общественной организацией «Делова Россия» («Открытый бизнес»)\n" +
                    "\n" +
                    "Спикер: Гончаров Михаил Петрович, член Координационного совета «Деловой России», владелец сети ресторанов «Теремок»\n" +
                    "\n" +
                    "Мероприятия проходит в рамках реализации Федерального проекта \"Платформа университетского технологического предпринимательства\" - Предпринимательская точка кипения при поддержке АНО Национальной технологической инициативы и Министерства науки и высшего образования РФ.\n" +
                    "\n" +
                    "#ПТК #Предпринимательская_ТК #ПТК_РЭУ #ТочкаКипенияРЭУ #ПУТП",
            arrayOf("https://leader-id.storage.yandexcloud.net/upload/1041496/f6223668-0b69-4b18-bc16-13a4ac4a5a45.png"),
            "Общественное",
            "11.02.2023 13:00",
            "Москва, Точка кипения РЭУ им. Г.В. Плеханова",
            24
        )
        eventList.add(event2)
        val event3 = Event(
            1612,
            "Бизнес-турнир Business Day. Стратегическая игра о стартапах",
            "С октября 2022 года по апрель 2023 года в рамках клуба «Junior Business Skills» для обучающихся школ и студентов колледжей пройдет Бизнес-турнир «Business day» – это настольная экономическая стратегическая игра о стартапах, кредитах, инвестициях, конкуренции и прибыльных отраслях.\n" +
                    "\n" +
                    "Миссия игры – стать владельцем успешной бизнес-империи и первым создать 7 своих компаний. Ребятам предстоит торговаться на аукционах, контролировать отрасли, покупать и открывать собственные компании.\n" +
                    "\n" +
                    "Стать участником бизнес-турнира можно, пройдя регистрацию на сайте spo.mosmetod.ru\n" +
                    "\n" +
                    "Педагог-наставник:\n" +
                    "\n" +
                    "открывает личный кабинет;\n" +
                    "выбирает дату проведение турнира;\n" +
                    "регистрирует до 4-х команд.\n" +
                    "Участники бизнес-турнира награждаются сертификатами и дипломами победителя, которые размещаются в личных кабинетах педагогов-наставников на сайте spo.mosmetod.ru.",
            arrayOf("https://leader-id.storage.yandexcloud.net/event_photo/304147/629db788ecb23224720337.jpg"),
            "Учебное",
            "21.03.2023 16:00",
            "Москва, Точка кипения РЭУ им. Г.В. Плеханова",
            24
        )
        eventList.add(event3)
        val event4 = Event(
            2323,
            "Методологический семинар для аспирантов 1 года обучения",
            "Научные руководители и аспиранты, имеющие значимые научные достижения, расскажут о траектории научного развития и выборе оптимальной научной методики, а также обсудят основные направления научных исследований.",
            arrayOf("https://leader-id.storage.yandexcloud.net/upload/91121/5a290da8-8f0c-4fd6-a1f6-fa3a08f6605b.jpg"),
            "Учебное, Культурное",
            "02.03.2023 15:30",
            "Москва, Точка кипения Тимирязевка",
            24
        )
        eventList.add(event4)
        setMyEventAdapter(eventList, view, requireContext())

        //Слушатель поисковика
        myEventsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean { //Поиск по карточкам

                val searchEventList = ArrayList<Event>()
                eventList.forEach {
                    if(it.title.contains(query!!, ignoreCase = true)){
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

    private fun setMyEventAdapter(events: ArrayList<Event>, view: View, context: Context){ //Адаптер текущих игр
        val recyclerView: RecyclerView = view.findViewById(R.id.myEventsRecyclerView) //Подвязка ресайклера к объекту
        val linearLayoutManager = LinearLayoutManager(context) //Подготовка лайаут менеджера
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager //Инициализация лайаут менеджера
        val presenceAdapter = MyEventAdapter(events, context) //внесение данных из листа в адаптер (заполнение данными)
        recyclerView.adapter = presenceAdapter //внесение данных из листа в адаптер (заполнение данными)

        presenceAdapter.onItemClick = {

            val event1 = Event(
                1,
                "Встреча сети Точек кипения: 2023 год",
                "1 февраля в 10:00 мск приглашаем команды Точек кипения на общую встречу Сети.\n" +
                        "\n" +
                        "Обсудим:\n" +
                        "\n" +
                        "▫️Ключевые события 2023 года\n" +
                        "\n" +
                        "▫️Апрельский съезд\n" +
                        "\n" +
                        "▫️Архипелаг 2023\n" +
                        "\n" +
                        "▫️Технопром\n" +
                        "\n" +
                        "▫️Баркемп 2023\n" +
                        "\n" +
                        "А также, ключевые темы года.",
                arrayOf("https://leader-id.storage.yandexcloud.net/upload/436356/7cc889c7-b2da-4077-883a-36b9111c930f.jpeg", "https://leader-id.storage.yandexcloud.net/upload/1041496/f6223668-0b69-4b18-bc16-13a4ac4a5a45.png"),
                "Общественное",
                "1 февраля, с 10:00 до 11:30",
                "Онлайн при поддержке Точка кипения - Москва",
                12
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
    }
}