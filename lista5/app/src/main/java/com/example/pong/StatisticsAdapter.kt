package com.example.pong

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pong.database.Player

class StatisticsAdapter(private val elementsList: List<Player>) : RecyclerView.Adapter<StatisticsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name : TextView = view.findViewById(R.id.name)
        val points : TextView = view.findViewById(R.id.points)
        val won : TextView = view.findViewById(R.id.won)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = elementsList[position].name
        holder.points.text = elementsList[position].points.toString()
        holder.won.text = elementsList[position].games.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_statistics, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return elementsList.size
    }


}