package com.example.ss_companionapp

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Base64
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.*


class RuneConfigurator : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rune_configurator)

        //LOAD IMG:
        var bmp: Bitmap? = null
        val bitmapName: String? = getIntent().getStringExtra("img")
        val fileInput: FileInputStream = this.openFileInput(bitmapName)
        bmp = BitmapFactory.decodeStream(fileInput)
        fileInput.close()

        val image: ImageView = findViewById(R.id.newRune)
        image.setImageBitmap(bmp)

        val getInfo: ImageButton = findViewById(R.id.infoButton)

        val save_button: Button = findViewById(R.id.save)
        val cancel_action: Button = findViewById(R.id.cancel)
        val runeName: TextView = findViewById(R.id.runeName)
        var savedRuneName: String = "Text"

        val fileDirectory = File(Environment.getExternalStorageDirectory(), "Documents")

        getInfo.setOnClickListener()
        {
            val toastToshow: Toast
            toastToshow = Toast.makeText(this, "Define the values you want for your Rune. Percentage correspond to the amount of percentages of one stat. 150% is double and 50% is half", Toast.LENGTH_LONG)
            toastToshow.show()
        }

        fun saveImageToExternalStorage(bitmap: Bitmap): Uri {
            val dir = File(Environment.getExternalStorageDirectory(), "Documents")

            if (!dir.exists()) {
                dir.mkdirs()
            }
            savedRuneName = runeName.text.toString() + (runeArray.count() + 1).toString()
            val file = File(dir, savedRuneName)

            try {
                val stream: OutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.flush()
                stream.close()
                toast("Rune created!")
            } catch (e: IOException) {
                e.printStackTrace()
                toast("An error occurred. Please, try again.")
            }
            return Uri.parse(file.absolutePath)
        }


        var atckSpeedSeekBar: SeekBar = findViewById(R.id.attack_speed)
        var abilityPowerSeekBar: SeekBar = findViewById(R.id.ability_power)
        var normAtckSeekBar: SeekBar = findViewById(R.id.normal_attack)
        var heavyAtckSeekBar: SeekBar = findViewById(R.id.heavy_attack)
        var atckSpeedValue: TextView = findViewById(R.id.speedValue)
        var abilityPowerValue: TextView = findViewById(R.id.abilityValue)
        var normalAtckValue: TextView = findViewById(R.id.normalAtckValue)
        var heavyAtckValue: TextView = findViewById(R.id.heavyAtckValue)
        var speedSeekBarListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!fromUser) {
                    return
                }
                // TEXT:
                atckSpeedValue.text = (100 + progress - 50).toString() + "%"
                normalAtckValue.text = (200 - (100 + progress - 50)).toString() + "%"
                // VALUES:
                normAtckSeekBar.progress = 100 - progress

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        }
        atckSpeedSeekBar.setOnSeekBarChangeListener(speedSeekBarListener)

        var abilitySeekBarListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!fromUser) {
                    return
                }
                abilityPowerValue.text = (100 + progress - 50).toString() + "%"
                heavyAtckValue.text = (200 - (100 + progress - 50)).toString() + "%"

                heavyAtckSeekBar.progress = 100 - progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        }
        abilityPowerSeekBar.setOnSeekBarChangeListener(abilitySeekBarListener)

        var normAtckSeekBarListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!fromUser) {
                    return
                }
                normalAtckValue.text = (100 + progress - 50).toString() + "%"
                atckSpeedValue.text = (200 - (100 + progress - 50)).toString() + "%"

                atckSpeedSeekBar.progress = 100 - progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        }
        normAtckSeekBar.setOnSeekBarChangeListener(normAtckSeekBarListener)

        var heavyAtckSeekBarListener = object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (!fromUser) {
                    return
                }
                heavyAtckValue.text = (100 + progress - 50).toString() + "%"
                abilityPowerValue.text = (200 - (100 + progress - 50)).toString() + "%"

                abilityPowerSeekBar.progress = 100 - progress
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        }
        heavyAtckSeekBar.setOnSeekBarChangeListener(heavyAtckSeekBarListener)

        runeName.setOnClickListener()
        {
            val input = EditText(this)
            val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            )
            input.layoutParams = lp
            val alertDialog: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
            alertDialog.setTitle("Name your Rune")
            alertDialog.setView(input)

            alertDialog.setPositiveButton("Confirm",
                    DialogInterface.OnClickListener { dialog, which ->
                        val name = input.text.toString()
                        runeName.text = name
                    })
            alertDialog.setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
            alertDialog.show()
        }

        save_button.setOnClickListener()
        {
            val uri: Uri = saveImageToExternalStorage(bmp)
            val client = OkHttpClient.Builder().build()
            val newRune = Rune(runeName.text.toString(), normAtckSeekBar.progress, heavyAtckSeekBar.progress, abilityPowerSeekBar.progress, atckSpeedSeekBar.progress, false, true)
            val outputStream = ByteArrayOutputStream()
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            val image = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)

            val objectMapper = jacksonObjectMapper()
            var jsonObject = JSONObject()
            jsonObject.put("rune", objectMapper.writeValueAsString(newRune))
            jsonObject.put("userid", user_id)
            jsonObject.put("image", image)
            val mediaType = "application/json; charset=utf-8".toMediaType()
            val body = jsonObject.toString().toRequestBody(mediaType)

            val request = Request.Builder().url("http://www.singingsands.tk:3000/myrunes/insert").post(body).build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                    }

                }
            })

            startActivity(Intent(this, CustomRunesInventory::class.java))
            this.finish()
        }

        cancel_action.setOnClickListener()
        {
            startActivity(Intent(this, MainMenu::class.java))
            this.finish()
        }

    }
}
