package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.CalendarView.OnDateChangeListener
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity() {
    var Day = ""
    var Month = ""
    var Year = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        initializeSpinner()

        findViewById<CalendarView>(R.id.enterDate).setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->
            Day = dayOfMonth.toString()
            Month = month.toString()
            Year = year.toString()
        })
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

        val date= "$Day/$Month/$Year"
        myIntent.putExtra("date", date)

        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)
        val timeView = linearLayout.getChildAt(0) as TextView
        val time: String = timeView.text.toString()
        myIntent.putExtra("time", time)

        setResult(RESULT_OK, myIntent)
        finish()
    }

    fun initializeSpinner() {
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

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }
    }

    fun setTime(view: View) {
        val myIntent = Intent(this, SetTimeActivity::class.java)
        startActivityForResult(myIntent, CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CODE) {
            val time: String = data?.getStringExtra("time").toString()

            findViewById<TextView>(R.id.enterTime).text = time
        }
    }

    companion object {
        const val CODE = 99
    }
}