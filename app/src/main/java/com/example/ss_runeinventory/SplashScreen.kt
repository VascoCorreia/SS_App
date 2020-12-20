package com.example.ss_runeinventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.content.Intent
import android.widget.ProgressBar

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private val SPLASH_TIME: Long = 5000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }



}