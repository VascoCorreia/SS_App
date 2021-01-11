package com.example.ss_companionapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class GameRunesInventory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_runes_inventory)

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView_runes);

        val fileDirectory = File(Environment.getExternalStorageDirectory(), "Documents")
        val Rune1 = Rune("Ability Power Rune", 0, -15, 15, 5, File(fileDirectory, "2.png"))
        val Rune2 = Rune("Af2", 124, -15, 15, 5, File(fileDirectory, "2.png"))
        val Rune3 = Rune("Af3", 145130, -15, 15, 5, File(fileDirectory, "2.png"))

        val runeArray = arrayOf(Rune1, Rune2, Rune3)
        recyclerView.adapter = MainAdapter(runeArray);
        recyclerView.layoutManager = LinearLayoutManager(this);

        recyclerView.setOnTouchListener(object: OnSwipe(this@GameRunesInventory) {
            override fun onSwipeLeft() {
                openCustomRunes()

            }

        })

    }

    fun openCustomRunes()
    {
        var intent = Intent(this,CustomRunesInventory::class.java)
        startActivity(intent)
        this.finish()
    }
}