package com.example.event_system_app.Activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.example.event_system_app.Helper.ServerHelper
import com.example.event_system_app.Model.User
import com.google.android.material.appbar.MaterialToolbar
import com.example.event_system_app.R

class ScannerActivity : AppCompatActivity() {
    private lateinit var codeScanner: CodeScanner
    private lateinit var toolbar: MaterialToolbar
    private lateinit var scannerView: CodeScannerView

    private lateinit var eventTitle: String
    private lateinit var serverHelper: ServerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanner)
        init()
        eventTitle = intent.getStringExtra("event_title")!!
        if (ContextCompat.checkSelfPermission(this@ScannerActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this@ScannerActivity, arrayOf(Manifest.permission.CAMERA), 123)
        } else {
            startScanning()
        }

        setSupportActionBar(toolbar)
        toolbar.isTitleCentered = true
        title = eventTitle
        supportActionBar?.setHomeAsUpIndicator(R.drawable.icon_back)
        toolbar.navigationIcon = getDrawable(R.drawable.icon_back)
        serverHelper = ServerHelper(this)
        checkConnection()

        toolbar.setNavigationOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            true
        }

        codeScanner.isAutoFocusEnabled = false // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

    }

    private fun checkConnection() {
        if(!serverHelper.isOnline(this)){
            val i = Intent(this, NetworkErrorActivity::class.java)
            startActivity(i)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startScanning()
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun startScanning() {


        // Callbacks
        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                if(it.text.equals("test-qr-code")) {
                    val i = Intent(this, ResultScannerActivity::class.java)
                    val user = User(
                        123,
                        "Иванов Иван Иванович",
                        "https://bans.avexa-project.ru/theme/img/profile-pics/2.jpg",
                        "ИУКб-18-1",
                        it.text
                    )
                    i.putExtra("user", user)
                    i.putExtra("event_title", eventTitle)
                    startActivity(i)
                }
                else{
                    Toast.makeText(this, "QR-код не распознан...", Toast.LENGTH_SHORT).show()
                }
            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}",
                    Toast.LENGTH_LONG).show()
            }
        }

        scannerView.setOnClickListener {
            codeScanner.startPreview()
        }

        scannerView.isAutoFocusButtonVisible = false
        scannerView.isFlashButtonVisible = false
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()
        super.onPause()
    }

    //Инициализация компонентов
    private fun init(){
        toolbar = findViewById(R.id.toolbar)

        // Parameters (default values)
        scannerView = findViewById(R.id.scanner_view)
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = true // Whether to enable flash or not
    }
}