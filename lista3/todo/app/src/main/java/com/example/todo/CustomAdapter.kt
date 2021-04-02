package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter: RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private val elementsList: MutableList<List<String>> = ArrayList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView
        val title: TextView
        val date: TextView
        val time: TextView
        init {
            image = view.findViewById(R.id.image)
            title = view.findViewById(R.id.activityTitle)
            date = view.findViewById(R.id.date)
            time = view.findViewById(R.id.time)

            //view.setOnLongClickListener {
            //    //Snackbar.make(itemView, "$title", Snackbar.LENGTH_INDEFINITE).show()
            //    //this.delete(1)
            //    true
            //}
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val images = arrayOf(
                R.drawable.sport,
                R.drawable.sport2,
                R.drawable.work,
                R.drawable.visit,
                R.drawable.event,
                R.drawable.house
        )

        val i: Int = elementsList[position].get(0).toInt()
        holder.image.setImageResource(images[i])
        holder.title.text = elementsList[position].get(1)
        holder.date.text = elementsList[position].get(2)
        holder.time.text = elementsList[position].get(3)
    }

    override fun getItemCount() : Int {
        return elementsList.size
    }

    fun add(data: List<String>) {
        elementsList.add(data)
    }

    fun delete(index: Int) {
        elementsList.removeAt(index)
        notifyDataSetChanged()
    }
}
