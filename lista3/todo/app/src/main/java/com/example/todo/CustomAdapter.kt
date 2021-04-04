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
        val stars: ArrayList<ImageView> = arrayListOf(
                view.findViewById(R.id.star1),
                view.findViewById(R.id.star2),
                view.findViewById(R.id.star3),
                view.findViewById(R.id.star4),
                view.findViewById(R.id.star5)
        )
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
                R.drawable.beers,
                R.drawable.cycling,
                R.drawable.eye,
                R.drawable.girl,
                R.drawable.hairdresser,
                R.drawable.heart,
                R.drawable.kid,
                R.drawable.studying,
                R.drawable.tooth,
                R.drawable.trip,
                R.drawable.work,
                R.drawable.workout
        )

        val i: Int = elementsList[position][0].toInt()
        holder.image.setImageResource(images[i])
        holder.title.text = elementsList[position][1]
        holder.date.text = elementsList[position][2]
        holder.time.text = elementsList[position][3]
        setStars(elementsList[position][4].toFloat(), holder)
    }

    override fun getItemCount() : Int {
        return elementsList.size
    }

    fun add(data: List<String>) {
        elementsList.add(data)
    }

    fun delete(index: Int) {
        elementsList.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index, elementsList.size)
        notifyDataSetChanged()
    }

    private fun setStars(importance: Float, holder: ViewHolder) {
        var k = importance
        var o = 0
        for(i in 0 until importance.toInt()) {
            holder.stars[i].setImageResource(R.drawable.star)
            k--
            o++
        }
        if(k > 0) {
            holder.stars[o].setImageResource(R.drawable.half_star)
        }
    }
}
