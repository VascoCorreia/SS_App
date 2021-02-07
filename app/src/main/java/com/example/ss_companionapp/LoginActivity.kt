package com.example.ss_companionapp

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.provider.Contacts
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import ru.gildor.coroutines.okhttp.await
import java.io.IOException

public var user_id: Int = 0

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val username = findViewById<EditText>(R.id.loginPage_username)
        val password = findViewById<EditText>(R.id.loginPage_password)
        val login = findViewById<Button>(R.id.login)

        login.setOnClickListener {
            val client = OkHttpClient.Builder().build()
            val objectMapper = jacksonObjectMapper()
            var jsonObject = JSONObject()
            jsonObject.put("username", username.text.toString())
            jsonObject.put("password", password.text.toString())
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body = jsonObject.toString().toRequestBody(mediaType)

            val request = Request.Builder().url("http://www.singingsands.tk:3000/login/").post(body).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        var responseString = response.body!!.string()
                        var id : UserId = objectMapper.readValue<UserId>(responseString)
                        if(id.userid != -1)
                        {
                            user_id = id.userid
                            startActivity(Intent(this@LoginActivity, MainMenu::class.java))
                        }
                        if(id.userid == -1)
                        {
                            runOnUiThread {
                                Toast.makeText(this@LoginActivity, "Incorrect username or password", Toast.LENGTH_SHORT).show()
                            }

                        }

                    }
                }

            })
            /*client.newCall(getuserid).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use{
                        if(!response.isSuccessful) throw IOException("Unexpected code $response")
                        userid = response.body!!.string().toInt()
                    }
                }
            })*/

        }
    }
}

class UserId(val userid: Int)
{
}

