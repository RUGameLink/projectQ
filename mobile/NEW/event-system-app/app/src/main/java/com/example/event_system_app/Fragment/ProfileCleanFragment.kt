package com.example.event_system_app.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.event_system_app.Activity.MainActivity
import com.example.event_system_app.Activity.NetworkErrorActivity
import com.example.event_system_app.Helper.ServerHelper
import com.example.event_system_app.Helper.SharedPrefs
import com.example.event_system_app.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ProfileCleanFragment: Fragment() {
    private lateinit var singInButton: MaterialButton
    private lateinit var login_text_view: TextInputLayout
    private lateinit var password_text_view: TextInputLayout
    private lateinit var passwordText: TextInputEditText
    private lateinit var loginText: TextInputEditText
    private lateinit var singInButtonLK: MaterialButton
    private lateinit var progressBar: ProgressBar

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
        val view = inflater.inflate(R.layout.fragment_profile_clean, container, false)
        init(view)
        serverHelper = ServerHelper(requireContext())
        checkConnection()
        login_text_view = TextInputLayout(requireContext())
        password_text_view = TextInputLayout(requireContext())

        singInButton.setOnClickListener {
            if (loginText.text.toString().isEmpty()){
                loginText.error = getString(R.string.error)
            }
            if (passwordText.text.toString().isEmpty()){
                passwordText.error = getString(R.string.error)
            }
            if (loginText.text.toString().isNotEmpty() && passwordText.text.toString().isNotEmpty()) {
                login()
            }
        }

        singInButtonLK.setOnClickListener {
            login()
        }

        return view
    }

    //Переключение фрагмента при логине
    private fun login() {
        showLoginDialog()
    }

    private fun showLoginDialog() {
        val builder = MaterialAlertDialogBuilder(requireContext(), R.style.MaterialAlertDialog_Rounded)
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_login, null)
        builder.setView(dialogView)

        val studentRadioButton = dialogView.findViewById<RadioButton>(R.id.student_rbtn)
        val orgRadioButton = dialogView.findViewById<RadioButton>(R.id.org_rbtn)
        val dialog = builder.create()
        studentRadioButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            val rnds = (1000..3000).random()
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    orgRadioButton.isChecked = false
                    studentRadioButton.isChecked = true
                    loginPrefs.setLoginCount(1)
                    progressBar.visibility = View.INVISIBLE
                    dialog.dismiss()
                    goToMain()
                },
                rnds.toLong() // value in milliseconds
            )
        }

        orgRadioButton.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            val rnds = (1000..3000).random()
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    studentRadioButton.isChecked = false
                    orgRadioButton.isChecked = true
                    loginPrefs.setLoginCount(2)
                    progressBar.visibility = View.INVISIBLE
                    dialog.dismiss()
                    goToMain()
                },
                rnds.toLong() // value in milliseconds
            )
        }
        dialog.show()
    }

    private fun goToMain(){
        val i = Intent(context, MainActivity::class.java)
        requireContext().startActivity(i)
    }

    //Инициализация компонентов
    private fun init(view: View) {
        singInButton = view.findViewById(R.id.singInButton)
        login_text_view = view.findViewById(R.id.login_text_view)
        password_text_view = view.findViewById(R.id.password_text_view)
        passwordText = view.findViewById(R.id.passwordText)
        loginText = view.findViewById(R.id.loginText)
        singInButtonLK = view.findViewById(R.id.singInButtonLK)
        progressBar = view.findViewById(R.id.progressBar)

        progressBar.visibility = View.INVISIBLE
    }

    private fun checkConnection() {
        if(!serverHelper.isOnline(requireContext())){
            val i = Intent(requireContext(), NetworkErrorActivity::class.java)
            startActivity(i)
        }
    }
}