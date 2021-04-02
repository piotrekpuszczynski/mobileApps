package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SetTimeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_time)
    }

    fun ok(view: View) {
        val myIntent = Intent()
        val timePicker = findViewById<TimePicker>(R.id.getTime)
        val Hour = timePicker.hour
        val Minute = timePicker.minute

        val time= "$Hour:$Minute"
        myIntent.putExtra("time", time)
        setResult(RESULT_OK, myIntent)
        finish()
    }
}