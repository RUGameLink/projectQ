package com.example.event_system_app.Activity

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.event_system_app.Model.MyEvent
import com.example.event_system_app.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.itextpdf.text.Element.ALIGN_CENTER
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfWriter

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*



class MyEventActivity: AppCompatActivity()  {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var headerMyText: TextView
    private lateinit var myTagListText: TextView
    private lateinit var myEventDateText: TextView
    private lateinit var qrImg: ImageView
    private lateinit var downloadButton: FloatingActionButton
    private lateinit var shareButton: FloatingActionButton
    private lateinit var calendarButton: FloatingActionButton
    private lateinit var myEventLocationText: TextView
    private lateinit var eventPageButton: MaterialButton
    private lateinit var cancelButton: MaterialButton

    private var STORAGE_CODE = 1001
    private lateinit var event: MyEvent
    private var pathToShare = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_event)
        init()
        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = getString(R.string.event_text)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.back_icon)
        toolbar.navigationIcon = getDrawable(R.drawable.back_icon)
        event = intent.getSerializableExtra("myEvent") as MyEvent
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
            checkToSave()
        }

        shareButton.setOnClickListener {
            Toast.makeText(this, "One moment...", Toast.LENGTH_SHORT).show()
            checkToSave()
            startFileShareIntent()

        }

        qrImg.setOnClickListener {
            val i = Intent(this, QrActivity::class.java)
            i.putExtra("qr", event.qrImg)
            startActivity(i)
        }

        val mSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        var startTime = replaceDate(event.date)
        val mStartTime = mSimpleDateFormat.parse(startTime)

        calendarButton.setOnClickListener {
            val mIntent = Intent(Intent.ACTION_EDIT)
            mIntent.type = "vnd.android.cursor.item/event"
            mIntent.putExtra("beginTime", mStartTime.time)
            mIntent.putExtra("time", true)
            mIntent.putExtra("rule", "FREQ=YEARLY")
            mIntent.putExtra("title", "${event.title}")
            startActivity(mIntent)
        }
    }

    private fun replaceDate(date: String): String {
        var dateComponent = date.split('.', ' ')
        return "${dateComponent[2]}-${dateComponent[1]}-${dateComponent[0]}T${dateComponent[3]}:00"
    }

    //Отправка файла
    fun startFileShareIntent() {
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "*/*"
            flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            putExtra(
                Intent.EXTRA_SUBJECT,
                ""
            )
            putExtra(
                Intent.EXTRA_TEXT,
                ""
            )
            val fileURI = FileProvider.getUriForFile(
                this@MyEventActivity!!, this@MyEventActivity!!.packageName + ".provider",
                File(pathToShare)
            )
            putExtra(Intent.EXTRA_STREAM, fileURI)
        }
        startActivity(shareIntent)
    }

    //Проверка разрешения на сохранение
    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkToSave(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, STORAGE_CODE)
            }
            else{
                savePDF()
            }
        }
        else{
            savePDF()
        }
    }

    //Сохранение файла
    @RequiresApi(Build.VERSION_CODES.O)
    private fun savePDF() {
        try {
            val FONT = "/res/font/timesnewromanpsmt.ttf"
            val bf = BaseFont.createFont(FONT.toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED)
            val fontBold = Font(bf, 18f, Font.BOLD)
            val fontNormal = Font(bf, 14f, Font.NORMAL)
            val doc = com.itextpdf.text.Document()
            val fileName = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis())
            val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + fileName + ".pdf"
            pathToShare = filePath
            PdfWriter.getInstance(doc, FileOutputStream(filePath))
            doc.open()

            val bitmap =
                (qrImg.getDrawable().getCurrent() as BitmapDrawable).bitmap
            val bmp = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
            val stream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            val image = Image.getInstance(stream.toByteArray())
            image.alignment = ALIGN_CENTER

            val title = "${event.title}\n".toString().trim()
            val titleParagraph = Paragraph(title, fontBold)
            titleParagraph.alignment = ALIGN_CENTER
            val footer = "Место проведения   ${event.location}\n" +
                    "Дата начала   ${event.date}".toString().trim()
            val footerParagraph = Paragraph(footer, fontNormal)
            footerParagraph.alignment = ALIGN_CENTER

            doc.addAuthor("Рейтинг стипендии")
            doc.add(titleParagraph)
            doc.add((image))
            doc.add(footerParagraph)
            doc.close()

            Toast.makeText(this, "$fileName.pdf $filePath", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception){
            println(e)
        }
    }

    //Получение результата разрешения на сохранение
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode){
            STORAGE_CODE -> {
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    savePDF()
                }
                else{
                    Toast.makeText(this, "PERMISSION DENIED!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    //Установка информации в компоненты
    private fun setContent(event: MyEvent) {
        Glide.with(this)
            .load(event.qrImg)
            .asBitmap()
            .placeholder(R.drawable.events_icon)
            .into(qrImg)

        headerMyText.text = event.title
        myTagListText.text = event.tags
        myEventDateText.text = event.date
        myEventLocationText.text = event.location
    }

    //Создание меню настройки
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.settings_menu, menu)
        return true
    }

    //Слушатель элементов меню
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

    //Инициализация компонентов
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
        calendarButton = findViewById(R.id.calendarButton)
    }
}