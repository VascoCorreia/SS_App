package com.example.ss_companionapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainMenu : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu)
    }

    fun clickHist(view: View) {
        startActivity(Intent(this, BattleScreen::class.java))
    }

    fun clickForge(view: View) {
        // startActivity(Intent(this, DrawTheRune::class.java))
        startActivity(Intent(this, DrawingScreen::class.java))
    }

    fun clickColl(view: View) {
        startActivity(Intent(this, GameRunesInventory::class.java))
    }



}