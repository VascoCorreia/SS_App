package com.example.ss_companionapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException

class GameRunesInventory : AppCompatActivity(), MainAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val client = OkHttpClient.Builder().build()
        val objectMapper = jacksonObjectMapper()
        setContentView(R.layout.activity_game_runes_inventory)

        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView_runes)
        val backButton: ImageButton = findViewById(R.id.backToMenu)
        val forthButton: ImageButton = findViewById(R.id.toCustomRunes)

        val request = Request.Builder().url("http://www.singingsands.tk:3000/gamerunesinv/" + user_id).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    var responseString = response.body!!.string()
                    if(response.isSuccessful && responseString != "No runes yet")
                    {
                        runeArray = objectMapper.readValue(responseString)
                        runOnUiThread(Runnable{
                            recyclerView.adapter = MainAdapter(runeArray.toTypedArray(), this@GameRunesInventory);
                        })
                    }
                    if(responseString == "No runes yet")
                    {
                        runOnUiThread(Runnable{
                            /*val textview: TextView = TextView(this@GameRunesInventory)
                            textview.text = "You do not have any Game-obtained runes yet"
                            recyclerView.addView(textview)*/
                            runeArray = mutableListOf<Rune>()
                            Toast.makeText(this@GameRunesInventory, "You do not have any runes yet", LENGTH_LONG).show()
                        })

                    }

                }
            }

        })

        backButton.setOnClickListener()
        {
            startActivity(Intent(this, MainMenu::class.java))
            this.finish()
        }

        forthButton.setOnClickListener()
        {
            openCustomRunes()
        }

        val fileDirectory = File(Environment.getExternalStorageDirectory(), "Documents")

        recyclerView.adapter = MainAdapter(runeArray.toTypedArray(), this)
        recyclerView.layoutManager = LinearLayoutManager(this);
        recyclerView.setOnTouchListener(object: OnSwipe(this@GameRunesInventory) {
            override fun onSwipeLeft() {
                openCustomRunes()
            }
        })
    }


    fun equipRune()
    {
        toast("Rune equipped!")
    }

    fun unequipRune()
    {
        toast("Rune unequipped!")
    }

    fun openCustomRunes()
    {
        var intent = Intent(this,CustomRunesInventory::class.java)
        startActivity(intent)
        this.finish()
    }

    override fun onItemClick(position: Int) {
        val client = OkHttpClient.Builder().build()
        val objectMapper = jacksonObjectMapper()
        var jsonObject = JSONObject()
        jsonObject.put("userid", user_id.toString())
        jsonObject.put("runeid", runeArray[position].id.toString())
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = jsonObject.toString().toRequestBody(mediaType)

        if(!runeArray[position].isEquipped)
        {
            val request = Request.Builder().url("http://www.singingsands.tk:3000/myrunes/equip/").post(body).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        var responseString = response.body!!.string()
                        runOnUiThread(Runnable {
                            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_runes)
                            runeArray[position].isEquipped = true
                            recyclerView.adapter = MainAdapter(runeArray.toTypedArray(), this@GameRunesInventory)
                            (recyclerView.adapter as MainAdapter).notifyItemChanged(position)
                            Toast.makeText(this@GameRunesInventory, "Rune equipped!", Toast.LENGTH_SHORT).show()
                        })
                    }
                }
            })
        }

        if(runeArray[position].isEquipped)
        {
            val request = Request.Builder().url("http://www.singingsands.tk:3000/myrunes/unequip/").post(body).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        var responseString = response.body!!.string()
                        runOnUiThread(Runnable {
                            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_runes)
                            runeArray[position].isEquipped = false
                            recyclerView.adapter = MainAdapter(runeArray.toTypedArray(), this@GameRunesInventory)
                            (recyclerView.adapter as MainAdapter).notifyItemChanged(position)
                            Toast.makeText(this@GameRunesInventory, "Rune unequipped!", Toast.LENGTH_SHORT).show()
                        })
                    }
                }
            })
        }
    }

    override fun onLongItemClick(position: Int) {
        val client = OkHttpClient.Builder().build()
        val objectMapper = jacksonObjectMapper()
        var jsonObject = JSONObject()
        jsonObject.put("userid", user_id.toString())
        jsonObject.put("runeid", runeArray[position].id.toString())
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body = jsonObject.toString().toRequestBody(mediaType)
        val request = Request.Builder().url("http://www.singingsands.tk:3000/myrunes/remove/").post(body).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    var responseString = response.body!!.string()
                    runOnUiThread(Runnable {
                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_runes)
                        runeArray.removeAt(position)
                        recyclerView.adapter = MainAdapter(runeArray.toTypedArray(), this@GameRunesInventory)
                        (recyclerView.adapter as MainAdapter).notifyItemChanged(position)
                        Toast.makeText(this@GameRunesInventory, "Removing the rune...", Toast.LENGTH_SHORT).show()
                    })
                }
            }
        })
    }
}