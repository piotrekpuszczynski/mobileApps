package com.example.pong

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.pong.database.AppDatabase
import com.example.pong.database.Player
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class StatisticsActivity : AppCompatActivity() {

    private lateinit var database : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        var playersList : List<Player>
        GlobalScope.launch {
            try {
                database = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "players.db"
                ).build()
                playersList = database.playerDao().getPlayers()
                val list = findViewById<RecyclerView>(R.id.recycler)
                list.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
                list.adapter = StatisticsAdapter(playersList)
            } catch (e: Exception) {}
        }
    }

    fun back(view: View) {
        finish()
    }
}