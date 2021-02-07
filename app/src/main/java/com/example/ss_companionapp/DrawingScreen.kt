package com.example.ss_companionapp

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.ViewGroup
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class DrawingScreen : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_screen)
        val mylayout = RelativeLayout(this)
        val myCanvasView = CanvasView(this)

        // creating the button
        val button_save = Button(this)
        val button_clear = Button(this)
        // setting layout_width and layout_height using layout parameters
        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val paramsClear = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        paramsClear.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        paramsClear.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        button_save.layoutParams = params
        button_clear.layoutParams = paramsClear
        button_save.text = "OK"
        button_clear.text = "CLEAR"
        // add Button to LinearLayout
        mylayout.addView(button_save)
        mylayout.addView(button_clear)
        mylayout.addView(myCanvasView)


        setContentView(mylayout)

        button_clear.setOnClickListener {
            myCanvasView.resetBitmap()

        }
        button_save.setOnClickListener {

            //val uri: Uri = saveImageToExternalStorage(myCanvasView.extraBitmap)
            val bitmapName: String = "bitmap.png"
            val stream = openFileOutput(bitmapName, Context.MODE_PRIVATE)
            myCanvasView.extraBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.close()
            val in1:Intent = Intent(this, RuneConfigurator::class.java)
            in1.putExtra("img", bitmapName)
            startActivity(in1)

        }
    }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun saveImageToExternalStorage(bitmap: Bitmap):Uri{
            val dir = File(Environment.getExternalStorageDirectory(), "Documents")

            if (!dir.exists())
            {
                dir.mkdirs()
            }
            val file = File(dir, "2.png")

            try {
                val stream: OutputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.flush()
                stream.close()
                toast("Rune created!")
            } catch (e: IOException){
                e.printStackTrace()
                toast("An error occurred. Please, try again.")
            }
            return Uri.parse(file.absolutePath)
        }
        }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
