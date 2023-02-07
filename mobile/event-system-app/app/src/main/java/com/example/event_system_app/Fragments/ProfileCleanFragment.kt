package com.example.event_system_app.Fragments

import android.content.Context
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.event_system_app.R
import com.google.android.material.button.MaterialButton

class ProfileCleanFragment: Fragment() {
    private lateinit var singInButton: MaterialButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_clean, container, false)
        init(view)

        singInButton.setOnClickListener {
            login(context)
        }
        return view
    }

    //Переключение фрагмента при логине
    private fun login(context: Context?) {
        setPref(delegate = 1, context)
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
        val profileFragment = ProfileFragment()
        var fragmentTransaction : FragmentTransaction = requireFragmentManager().beginTransaction()
        fragmentTransaction.replace(R.id.frame, profileFragment)
        fragmentTransaction.commit()
    }

    //Инициализация компонентов
    private fun init(view: View) {
        singInButton = view.findViewById(R.id.singInButton)
    }
}