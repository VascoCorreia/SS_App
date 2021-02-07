package com.example.ss_companionapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

public var historyArray = mutableListOf<History>()

class BattleScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_battle_screen)
        val client = OkHttpClient.Builder().build()
        val objectMapper = jacksonObjectMapper()
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView_battle_history)
        recyclerView.layoutManager = LinearLayoutManager(this);
        val backButton: ImageButton = findViewById(R.id.backToMenu)

        val request = Request.Builder().url("http://www.singingsands.tk:3000/battles/" + user_id).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    var responseString = response.body!!.string()
                    historyArray = objectMapper.readValue<MutableList<History>>(responseString)
                    runOnUiThread(Runnable{
                        recyclerView.adapter = BattleAdapter(historyArray.toTypedArray());
                    })
                }
            }

        })

        backButton.setOnClickListener()
        {
            backToMenu()
        }
    }

    fun backToMenu()
    {
        var intent = Intent(this, MainMenu::class.java)
        startActivity(intent)
        this.finish()
    }
}

