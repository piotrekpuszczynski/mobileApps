package com.example.pong

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class Player(
    @ColumnInfo(name = "name") @PrimaryKey var name : String,
    @ColumnInfo(name = "points") var points : Int = 0,
    @ColumnInfo(name = "games_won") var games : Int = 0
)