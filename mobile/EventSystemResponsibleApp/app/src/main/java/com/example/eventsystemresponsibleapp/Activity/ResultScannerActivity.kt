package com.example.eventsystemresponsibleapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.eventsystemresponsibleapp.Model.User
import com.example.eventsystemresponsibleapp.R
import com.google.android.material.button.MaterialButton

class ResultScannerActivity : AppCompatActivity() {
    private lateinit var userImage: ImageView
    private lateinit var nameUserText: TextView
    private lateinit var userGroupText: TextView
    private lateinit var confirmButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_scanner)
        init()

        val user = intent.getSerializableExtra("user") as User

        setInfo(user)
    }

    private fun setInfo(user: User) {
        nameUserText.text = user.userName
        userGroupText.text = user.userGroup
        Glide.with(this)
            .load(user.userImageURL)
            .placeholder(R.drawable.icon_events)
            .into(userImage);
    }

    private fun init(){
        userImage = findViewById(R.id.userImage)
        nameUserText = findViewById(R.id.nameUserText)
        userGroupText = findViewById(R.id.userGroupText)
        confirmButton = findViewById(R.id.confirm_button)
    }
}