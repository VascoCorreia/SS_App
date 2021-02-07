package com.example.ss_companionapp

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.Environment
import android.widget.ImageButton
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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

public var runeArray = mutableListOf<Rune>()

class CustomRunesInventory : AppCompatActivity(), MainAdapter.OnItemClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val client = OkHttpClient.Builder().build()
        val objectMapper = jacksonObjectMapper()
        setContentView(R.layout.activity_custom_runes_inventory)
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView_custom_runes);
        val backButton: ImageButton = findViewById(R.id.backtoGrunes)

        val request = Request.Builder().url("http://www.singingsands.tk:3000/myrunesinv/" + user_id).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    var responseString = response.body!!.string()
                    runeArray = objectMapper.readValue(responseString)
                    runOnUiThread(Runnable{
                        recyclerView.adapter = MainAdapter(runeArray.toTypedArray(), this@CustomRunesInventory);
                    })
                }
            }

        })
        /*val fileDirectory = File(Environment.getExternalStorageDirectory(), "Documents")

        for(i in fileDirectory.list()){
            runeArray.add(Rune(i.toString(), 1,3,4,5, File(fileDirectory, i)))
        }*/

        backButton.setOnClickListener()
        {
            backToGameRunes()
        }

        recyclerView.adapter = MainAdapter(runeArray.toTypedArray(), this);
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
                            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_custom_runes)
                            runeArray[position].isEquipped = true
                            recyclerView.adapter = MainAdapter(runeArray.toTypedArray(), this@CustomRunesInventory)
                            (recyclerView.adapter as MainAdapter).notifyItemChanged(position)
                            Toast.makeText(this@CustomRunesInventory, "Rune equipped!", LENGTH_SHORT).show()
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
                            val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_custom_runes)
                            runeArray[position].isEquipped = false
                            recyclerView.adapter = MainAdapter(runeArray.toTypedArray(), this@CustomRunesInventory)
                            (recyclerView.adapter as MainAdapter).notifyItemChanged(position)
                            Toast.makeText(this@CustomRunesInventory, "Rune unequipped!", LENGTH_SHORT).show()
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
                        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_custom_runes)
                        runeArray.removeAt(position)
                        recyclerView.adapter = MainAdapter(runeArray.toTypedArray(), this@CustomRunesInventory)
                        (recyclerView.adapter as MainAdapter).notifyItemChanged(position)
                        Toast.makeText(this@CustomRunesInventory, "Removing the rune...", Toast.LENGTH_SHORT).show()
                    })
                }
            }
        })
    }
}