package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        val year = "${picker.year}"
        myIntent.putExtra("year", year)

        val month = "${picker.month + 1}"
        myIntent.putExtra("month", month)

        val day = "${picker.dayOfMonth}"
        myIntent.putExtra("day", day)

        val timePicker = findViewById<TimePicker>(R.id.getTime)
        val hour = timePicker.hour.toString()
        myIntent.putExtra("hour", hour)

        var minute = timePicker.minute.toString()
        if(minute.length == 1) {
            minute = "0$minute"
        }
        myIntent.putExtra("minute", minute)

        val importance = findViewById<RatingBar>(R.id.ratingBar).rating.toString()
        myIntent.putExtra("importance", importance)

        setResult(RESULT_OK, myIntent)
        finish()
    }

    private fun initialiseSpinner() {
        val spinner: Spinner = findViewById(R.id.spinner)
        ArrayAdapter.createFromResource(
                this,
                R.array.images,
                R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long) {
                val image = findViewById<ImageView>(R.id.enterImage)

                val images = arrayOf(
                        R.drawable.beers,
                        R.drawable.cycling,
                        R.drawable.eye,
                        R.drawable.girl,
                        R.drawable.hairdresser,
                        R.drawable.heart,
                        R.drawable.kid,
                        R.drawable.studying,
                        R.drawable.tooth,
                        R.drawable.trip,
                        R.drawable.work,
                        R.drawable.workout
                )

                image.setImageResource(images[position])
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }
    }
}
