package com.example.ss_companionapp

import android.app.ActionBar
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout

class DrawingScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawing_screen)
        val mylayout = RelativeLayout(this)
        val myCanvasView = CanvasView(this)

        // creating the button
        val button_dynamic = Button(this)
        val button_clear = Button(this)
        // setting layout_width and layout_height using layout parameters
        val params = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val paramsClear = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
        paramsClear.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        paramsClear.addRule(RelativeLayout.ALIGN_PARENT_LEFT)
        button_dynamic.layoutParams = params
        button_clear.layoutParams = paramsClear
        button_dynamic.text = "SAVE"
        button_clear.text = "CLEAR"
        // add Button to LinearLayout
        mylayout.addView(button_dynamic)
        mylayout.addView(button_clear)
        mylayout.addView(myCanvasView)

        setContentView(mylayout)

        button_clear.setOnClickListener {
            myCanvasView.resetBitmap()

        }
    }
}