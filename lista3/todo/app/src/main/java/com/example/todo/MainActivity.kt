package com.example.todo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
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
            val date: String = data?.getStringExtra("date").toString()
            val time: String = data?.getStringExtra("time").toString()
            val importance: String = data?.getStringExtra("importance").toString()
            val element = arrayOf(
                    image,
                    title,
                    date,
                    time,
                    importance
            )

            val adapter = findViewById<RecyclerView>(R.id.todoList).adapter as CustomAdapter
            adapter.add(element.toList())
            adapter.notifyDataSetChanged()

        }
    }

    companion object {
        const val CODE = 100
    }
}
