package com.example.ss_companionapp

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class CustomRunesInventory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_custom_runes_inventory)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView_custom_runes);

        val fileDirectory = File(Environment.getExternalStorageDirectory(), "Documents")
        val runeArray = mutableListOf<Rune>()
        for(i in fileDirectory.list()){
            runeArray.add(Rune(i.toString(), 1,3,4,5, File(fileDirectory, i)))
        }

        recyclerView.adapter = MainAdapter(runeArray.toTypedArray());
        recyclerView.layoutManager = LinearLayoutManager(this);

        recyclerView.setOnTouchListener(object: OnSwipe(this@CustomRunesInventory) {
            override fun onSwipeRight() {
                backToGameRunes()

            }

        })
    }

    fun backToGameRunes()
    {
        var intent = Intent(this,GameRunesInventory::class.java)
        startActivity(intent)
        this.finish()
    }
}