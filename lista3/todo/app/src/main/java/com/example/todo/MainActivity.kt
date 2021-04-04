package com.example.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = findViewById<RecyclerView>(R.id.todoList)
        list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list.adapter = CustomAdapter(supportFragmentManager)

        initialiseSpinner()
    }

    fun createNew(view: View) {
        val myIntent = Intent(this, AddActivity::class.java)
        startActivityForResult(myIntent, CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CODE && resultCode == Activity.RESULT_OK) {
            val image: String = data?.getStringExtra("image").toString()
            val title: String = data?.getStringExtra("title").toString()
            val year: String = data?.getStringExtra("year").toString()
            val month: String = data?.getStringExtra("month").toString()
            val day: String = data?.getStringExtra("day").toString()
            val hour: String = data?.getStringExtra("hour").toString()
            val minute: String = data?.getStringExtra("minute").toString()
            val importance: String = data?.getStringExtra("importance").toString()
            val element = arrayOf(
                    image,
                    title,
                    year,
                    month,
                    day,
                    hour,
                    minute,
                    importance
            )

            val adapter = findViewById<RecyclerView>(R.id.todoList).adapter as CustomAdapter
            adapter.add(element.toMutableList() as ArrayList<String>)
            adapter.notifyDataSetChanged()
        }
    }

    companion object {
        const val CODE = 100
    }

    private fun initialiseSpinner() {
        val spinner: Spinner = findViewById(R.id.sort)
        ArrayAdapter.createFromResource(
                this,
                R.array.sort,
                R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View, position: Int, id: Long) {
                val list = findViewById<RecyclerView>(R.id.todoList)
                val adapter = list.adapter as CustomAdapter

                val selectedSort = selectedItemView as TextView
                when(selectedSort.text) {
                    "priority" -> adapter.sortByPriority()
                    "time" -> adapter.sortByTime()
                    "time desc" -> adapter.sortByTimeDesc()
                    "icon" -> adapter.sortByIcon()
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }
    }
}
