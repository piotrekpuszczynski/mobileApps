package com.example.pong

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    companion object {
        const val CODE = 100
    }

    private lateinit var database : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            try {
                database = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java,
                    "players.db"
                ).build()
            } catch (e: Exception) {}
        }
    }

    fun click(view: View) {
        when(view.id) {
            R.id.singleplayer -> {
                val intent = Intent(this, GameActivity::class.java)
                startActivityForResult(intent, CODE)
            } R.id.multiplayer -> {

            } else -> {

            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODE && resultCode == Activity.RESULT_OK) {
            GlobalScope.launch {
                database.playerDao().updatePoints("player one",
                    data?.getStringExtra("playerOnePoints")!!.toInt())
                database.playerDao().updatePoints("player two",
                    data.getStringExtra("playerTwoPoints")!!.toInt())
                database.playerDao().updateWinner(data.getStringExtra("won")!!, 1)
            }
        }
    }

}