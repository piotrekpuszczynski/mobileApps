package com.example.pong

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    //private lateinit var database : AppDatabase

    companion object {
        const val RC_SIGN_IN = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        GlobalScope.launch {
            try {
                database = Room.databaseBuilder(
                        applicationContext,
                        AppDatabase::class.java,
                        "players.db"
                ).build()
                database.playerDao().insertPlayer(Player("player one"))
                database.playerDao().insertPlayer(Player("player two"))

            } catch (e: Exception) {}
        } */

        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build())

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            //val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                //val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show()
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show()

                finish()
            }
        }
    }

    fun click(view: View) {
        when(view.id) {
            R.id.singleplayer -> {
                val intent = Intent(this, GameActivity::class.java)
                startActivity(intent)
            } R.id.multiplayer -> {
            val user = FirebaseAuth.getInstance().currentUser

            val users : ArrayList<String> = ArrayList()
            val rootRef = FirebaseDatabase.getInstance().reference

            val usersdRef = rootRef.child("users")

            Log.i("sss", usersdRef.toString())


        } else -> {
                val intent = Intent(this, StatisticsActivity()::class.java)
                startActivity(intent)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    // ...
                }
    }
}