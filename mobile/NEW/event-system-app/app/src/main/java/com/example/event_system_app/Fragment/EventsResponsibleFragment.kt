package com.example.event_system_app.Fragment

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.event_system_app.Activity.NetworkErrorActivity
import com.example.event_system_app.Adapter.EventResponsibleAdapter
import com.example.event_system_app.Helper.ServerHelper
import com.example.event_system_app.Model.Event
import com.example.event_system_app.R
import com.nex3z.togglebuttongroup.SingleSelectToggleGroup

class EventsResponsibleFragment: Fragment() {
    private lateinit var eventsSearchView: SearchView
    private lateinit var tagsGroup: SingleSelectToggleGroup

    private lateinit var serverHelper: ServerHelper
    private var tags: String = ""
    val eventList = ArrayList<Event>()

    override fun onCreate(savedInstanceState: Bundle?) {
        getActivity()?.setRequestedOrientation(
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_events_responsible, container, false)
        init(view)

        serverHelper = ServerHelper(requireContext())
        checkConnection()

        tagsGroup.check(R.id.anyToggle)

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
            12,
            "1 февраля, с 10:00 до 11:30",
            "1 февраля, с 10:00 до 11:30",
            "Иванов Иван\nКириченко Андрей\nПроскофья Смолина"
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
            "31 января, с 17:00 до 18:00",
            "Москва, Точка кипения РЭУ им. Г.В. Плеханова",
            24,
            "1 февраля, с 10:00 до 11:30",
            "1 февраля, с 10:00 до 11:30",
            "Иванов Иван\nКириченко Андрей\nПроскофья Смолина"
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
            "31 января, с 14:00 до 18:00",
            "Москва, Точка кипения РЭУ им. Г.В. Плеханова",
            24,
            "1 февраля, с 10:00 до 11:30",
            "1 февраля, с 10:00 до 11:30",
            "Иванов Иван\nКириченко Андрей\nПроскофья Смолина"
        )
        eventList.add(event3)
        val event4 = Event(
            2323,
            "Методологический семинар для аспирантов 1 года обучения",
            "Научные руководители и аспиранты, имеющие значимые научные достижения, расскажут о траектории научного развития и выборе оптимальной научной методики, а также обсудят основные направления научных исследований.",
            arrayOf("https://sun9-23.userapi.com/impg/vL1O2jRKfF6SAD3-yuV94T1mjq5rpveujxEzSw/OoyTm9iaHfk.jpg?size=2402x2160&quality=96&sign=96f9a64e3df758376239468ffaa279c7&type=album"),
            "Учебное, Культурное",
            "3 февраля, с 15:30 до 17:30",
            "Москва, Точка кипения Тимирязевка",
            24,
            "1 февраля, с 10:00 до 11:30",
            "1 февраля, с 10:00 до 11:30",
            "Иванов Иван\nКириченко Андрей\nПроскофья Смолина"
        )
        eventList.add(event4)
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
                    tagsGroup.check(R.id.anyToggle)
                }
                return false
            }
        })

        //Слушатель кнопок фильтра
        tagsGroup.setOnCheckedChangeListener { group, checkedId ->
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
        recyclerView.adapter = EventResponsibleAdapter(events, context) //внесение данных из листа в адаптер (заполнение данными)
    }

    //Инициализация компонентов
    private fun init(view: View) {
        eventsSearchView = view.findViewById(R.id.eventsSearchView)
        tagsGroup = view.findViewById(R.id.tags_group)
    }

    private fun checkConnection() {
        if(!serverHelper.isOnline(requireContext())){
            val i = Intent(requireContext(), NetworkErrorActivity::class.java)
            startActivity(i)
        }
    }
}