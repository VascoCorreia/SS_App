package com.example.ss_runeinventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameRunesInventory : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_runes_inventory)

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView_runes);

        recyclerView.adapter = MainAdapter();
        recyclerView.layoutManager = LinearLayoutManager(this);

    }
}