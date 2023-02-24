package com.example.eventsystemresponsibleapp.Fragment

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.eventsystemresponsibleapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment: Fragment() {
    private lateinit var userImage: ImageView
    private lateinit var nameUserText: TextView
    private lateinit var userRoleText: TextView
    private lateinit var logOutButton: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        init(view)

        nameUserText.text = "Иванов Иван Иванович"
        userRoleText.text = "ИСМб-19-1"

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
        setPref(delegate = 0, context)
        replaceFragment()
    }

    //Установка префа логина
    private fun setPref(delegate: Int, context: Context?){
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putInt("login", delegate).apply()
    }

    //Смена фрагмента
    private fun replaceFragment(){
        val profileFragment = ProfileCleanFragment()
        var fragmentTransaction : FragmentTransaction = requireFragmentManager().beginTransaction()
        fragmentTransaction.replace(R.id.frame, profileFragment)
        fragmentTransaction.commit()
    }

    //Инициализация компонентов
    private fun init(view: View) {
        userImage = view.findViewById(R.id.userImage)
        nameUserText = view.findViewById(R.id.nameUserText)
        userRoleText = view.findViewById(R.id.userRoleText)
        logOutButton = view.findViewById(R.id.logOutButton)
    }
}