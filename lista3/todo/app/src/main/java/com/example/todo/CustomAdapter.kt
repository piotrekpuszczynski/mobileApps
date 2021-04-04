package com.example.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class CustomAdapter(supportFragmentManager: FragmentManager) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private val elementsList: MutableList<ArrayList<String>> = ArrayList()
    private val elementsListToSort: MutableList<ArrayList<String>> = ArrayList()
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
        holder.date.text = elementsList[position][2] + "/" + elementsList[position][3] + "/" + elementsList[position][4]
        holder.time.text = elementsList[position][5] + ":" + elementsList[position][6]
        setStars(elementsList[position][7].toFloat(), holder)
    }

    override fun getItemCount() : Int {
        return elementsList.size
    }

    fun add(data: ArrayList<String>) {
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

    fun sortTime() {

        for(i in 0 until elementsList.size) elementsListToSort.add(elementsList[i])

        var key: ArrayList<String>
        var i: Int
        for (j in 1 until elementsListToSort.size) {
            key = elementsListToSort[j]
            i = j - 1
            while (i >= 0 &&
                    (LocalDate.of(key[2].toInt(), key[3].toInt(), key[4].toInt()).isBefore(LocalDate.of(elementsListToSort[i][2].toInt(), elementsListToSort[i][3].toInt(), elementsListToSort[i][4].toInt())) ||
                    LocalDate.of(key[2].toInt(), key[3].toInt(), key[4].toInt()).isEqual(LocalDate.of(elementsListToSort[i][2].toInt(), elementsListToSort[i][3].toInt(), elementsListToSort[i][4].toInt())))) {
                elementsListToSort[i + 1] = elementsListToSort[i]
                i--
            }
            elementsListToSort[i + 1] = key
        }

        for(i in 0 until elementsList.size) delete(0)
        for(i in 0 until elementsListToSort.size) add(elementsListToSort[i])
        for(i in 0 until elementsListToSort.size) elementsListToSort.removeAt(0)
    }
}
