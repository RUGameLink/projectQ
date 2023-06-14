package com.example.event_system_app.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.event_system_app.Activity.MainActivity
import com.example.event_system_app.Activity.NetworkErrorActivity
import com.example.event_system_app.Helper.ServerHelper
import com.example.event_system_app.Helper.SharedPrefs
import com.example.event_system_app.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment: Fragment() {
    private lateinit var userImage: ImageView
    private lateinit var nameUserText: TextView
    private lateinit var userRoleText: TextView
    private lateinit var logOutButton: MaterialButton

    private lateinit var loginPrefs: SharedPrefs
    private lateinit var serverHelper: ServerHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginPrefs = SharedPrefs(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        init(view)
        serverHelper = ServerHelper(requireContext())
        checkConnection()
        if (loginPrefs.getLoginCount() == 1){
            nameUserText.text = "Иванов Иван Иванович"
            userRoleText.text = "ИСМб-19-1"
        }
        if (loginPrefs.getLoginCount() == 2){
            nameUserText.text = "Петров Петр Петрович"
            userRoleText.text = "Ответственный"
        }


        logOutButton.setOnClickListener {
            showLogOutDialog()
        }
        return view
    }

    //Запуск диалога выхода
    private fun showLogOutDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_Rounded)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_logout, null)
        builder.setView(dialogView)

        val yes_button = dialogView.findViewById<MaterialButton>(R.id.yes_button)
        val no_button = dialogView.findViewById<MaterialButton>(R.id.no_button)
        val dialog = builder.create()

        yes_button.setOnClickListener {
            logout()
            dialog.dismiss();
        }

        no_button.setOnClickListener {
            dialog.dismiss();
        }

        dialog.show()
    }

    //Смена фрагмента при выходе
    private fun logout() {
        loginPrefs.setLoginCount(0)
        goToMain()
    }

    private fun goToMain(){
        val i = Intent(context, MainActivity::class.java)
        requireContext().startActivity(i)
    }

    //Инициализация компонентов
    private fun init(view: View) {
        userImage = view.findViewById(R.id.userImage)
        nameUserText = view.findViewById(R.id.nameUserText)
        userRoleText = view.findViewById(R.id.userRoleText)
        logOutButton = view.findViewById(R.id.logOutButton)
    }

    private fun checkConnection() {
        if(!serverHelper.isOnline(requireContext())){
            val i = Intent(requireContext(), NetworkErrorActivity::class.java)
            startActivity(i)
        }
    }
}