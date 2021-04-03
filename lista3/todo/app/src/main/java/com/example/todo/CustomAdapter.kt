package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(supportFragmentManager: FragmentManager) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private val elementsList: MutableList<List<String>> = ArrayList()
    private var longClickListenerAdapter: LongClickListenerAdapter? = null
    val fragmentManager = supportFragmentManager

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.activityTitle)
        val date: TextView = view.findViewById(R.id.date)
        val time: TextView = view.findViewById(R.id.time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        longClickListenerAdapter = LongClickListenerAdapter()
        view.setOnLongClickListener(longClickListenerAdapter)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        longClickListenerAdapter?.setPositionAndRecycler(holder.adapterPosition, this)
        longClickListenerAdapter = null

        val images = arrayOf(
                R.drawable.sport,
                R.drawable.sport2,
                R.drawable.work,
                R.drawable.visit,
                R.drawable.event,
                R.drawable.house
        )
        val i: Int = elementsList[position][0].toInt()
        holder.image.setImageResource(images[i])
        holder.title.text = elementsList[position][1]
        holder.date.text = elementsList[position][2]
        holder.time.text = elementsList[position][3]
    }

    override fun getItemCount() : Int {
        return elementsList.size
    }

    fun add(data: List<String>) {
        elementsList.add(data)
    }

    fun delete(index: Int) {
        elementsList.removeAt(index)
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, elementsList.size)
        notifyDataSetChanged()
    }
}
