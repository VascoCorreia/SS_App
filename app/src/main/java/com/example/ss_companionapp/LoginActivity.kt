package com.example.ss_companionapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

import android.content.Intent

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.loginPage_username)
        val password = findViewById<EditText>(R.id.loginPage_password)
        val login = findViewById<Button>(R.id.login)

        login.setOnClickListener {

            var intent = Intent(this,MainMenu::class.java)
            startActivity(intent)

        }

    }

}