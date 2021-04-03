package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        initialiseSpinner()
    }

    fun cancel(view: View) {
        val myIntent = Intent()
        setResult(RESULT_CANCELED, myIntent)
        finish()
    }

    fun add(view: View) {
        val myIntent = Intent()

        val image: String = findViewById<Spinner>(R.id.spinner).selectedItemPosition.toString()
        myIntent.putExtra("image", image)

        val title: String = findViewById<EditText>(R.id.enterTitle).text.toString()
        myIntent.putExtra("title", title)

        val picker = findViewById<DatePicker>(R.id.enterDate)
        val date = "${picker.dayOfMonth}/${picker.month + 1}/${picker.year}"
        myIntent.putExtra("date", date)

        val timePicker = findViewById<TimePicker>(R.id.getTime)
        val hour = timePicker.hour.toString()
        var minute = timePicker.minute.toString()
        if(minute.length == 1) {
            minute = "0$minute"
        }

        val time= "$hour:$minute"
        myIntent.putExtra("time", time)

        setResult(RESULT_OK, myIntent)
        finish()
    }

    fun initialiseSpinner() {
        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.images_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long) {
                val image = findViewById<ImageView>(R.id.enterImage)

                val images = arrayOf(
                        R.drawable.sport,
                        R.drawable.sport2,
                        R.drawable.work,
                        R.drawable.visit,
                        R.drawable.event,
                        R.drawable.house
                )

                image.setImageResource(images[position])
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }
    }
}