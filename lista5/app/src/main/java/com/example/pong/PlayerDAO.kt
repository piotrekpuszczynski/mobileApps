package com.example.pong

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PlayerDAO {
    @Query("select * from players")
    fun getPlayers() : List<Player>

    @Insert
    fun insertPlayer(vararg player : Player)

    @Query("update players set points = points + :newPoints where name = :player")
    fun updatePoints(player : String, newPoints : Int) : Int

    @Query("update players set games_won = games_won + :won where name = :player")
    fun updateWinner(player : String, won : Int) : Int
}