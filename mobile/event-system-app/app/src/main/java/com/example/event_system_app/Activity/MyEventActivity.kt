package com.example.event_system_app.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.event_system_app.Model.MyEvent
import com.example.event_system_app.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.nio.file.Paths

class MyEventActivity: AppCompatActivity()  {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var headerMyText: TextView
    private lateinit var myTagListText: TextView
    private lateinit var myEventDateText: TextView
    private lateinit var qrImg: ImageView
    private lateinit var downloadButton: FloatingActionButton
    private lateinit var shareButton: FloatingActionButton
    private lateinit var myEventLocationText: TextView
    private lateinit var eventPageButton: MaterialButton
    private lateinit var cancelButton: MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_event)
        init()
        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = getString(R.string.event_text)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_icon)
        toolbar.navigationIcon = getDrawable(R.drawable.back_icon)
        val event = intent.getSerializableExtra("myEvent") as MyEvent

        toolbar.setNavigationOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            true
        }
        setContent(event)

        eventPageButton.setOnClickListener {
            Toast.makeText(this, "Я открою страницу мероприятия", Toast.LENGTH_SHORT).show()
        }

        cancelButton.setOnClickListener {
            Toast.makeText(this, "Я отменю регистрацию", Toast.LENGTH_SHORT).show()
        }

        downloadButton.setOnClickListener {
            downloadFile(event)
        }

        shareButton.setOnClickListener {
            Toast.makeText(this, "Я расшарю файл", Toast.LENGTH_SHORT).show()
        }

        qrImg.setOnClickListener {
            val i = Intent(this, QrActivity::class.java)
            i.putExtra("qr", event.qrImg)
            startActivity(i)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun downloadFile(event: MyEvent){
        val pdf by lazy {
            HtmlToPdf(executable = "/usr/bin/wkhtmltopdf") {
                orientation(PageOrientation.LANDSCAPE)
                pageSize("Letter")
                marginTop("1in")
                marginBottom("1in")
                marginLeft("1in")
                marginRight("1in")
            }
        }

        val htmlString = "<html>\n" +
                "    <body>\n" +
                "        <h1 align=\"center\">${event.title} \"Лезвие Восход\"/h1>\n" +
                "        <p></p>\n" +
                "        <p></p>\n" +
                "        <center>\n" +
                "            <img src=\"${event.qrImg}\">\n" +
                "        </center>\n" +
                "        <p></p>\n" +
                "        <h2 align=\"center\">Дата проведения:&nbsp;${event.date}</h2>\n" +
                "        <h2 align=\"center\">Место проведения:&nbsp;${event.location}</h2>\n" +
                "    </body>\n" +
                "    </html>"

        val outputFile = Paths.get("/home/jasoet/document/destination.pdf").toFile()
        val inputStream = pdf.convert(input = file,output = outputFile) // will always return null if output is redirected

// Convert and return InputStream
        val inputStream = pdf.convert(input = file) // InputStream is open and ready to use
    }

    private fun setContent(event: MyEvent) {
        Glide.with(this)
            .load(event.qrImg)
            .placeholder(R.drawable.events_icon)
            .into(qrImg)

        headerMyText.text = event.title
        myTagListText.text = event.tags
        myEventDateText.text = event.date
        myEventLocationText.text = event.location
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings_item -> {
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun init(){
        toolbar = findViewById(R.id.toolbar)
        headerMyText = findViewById(R.id.headerMyText)
        myTagListText = findViewById(R.id.myTagListText)
        myEventDateText = findViewById(R.id.myEventDateText)
        qrImg = findViewById(R.id.qrImg)
        downloadButton = findViewById(R.id.downloadButton)
        shareButton = findViewById(R.id.shareButton)
        myEventLocationText = findViewById(R.id.myEventLocationText)
        eventPageButton = findViewById(R.id.event_page_button)
        cancelButton = findViewById(R.id.cancel_button)
    }
}