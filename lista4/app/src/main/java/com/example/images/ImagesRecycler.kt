package com.example.images

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.RatingBar
import androidx.recyclerview.widget.RecyclerView


class ImagesRecycler(
        array: ArrayList<ArrayList<String>>,
        columns: Int,
        width: Int,
        private val imagesFragment: ImagesFragment
) : RecyclerView.Adapter<ImagesRecycler.ViewHolder>() {
    private val imagesURI: ArrayList<ArrayList<String>> = array
    private val size = width / columns
    private val layoutParams = GridLayout.LayoutParams(ViewGroup.LayoutParams(size, size))

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.imageView)
        val rating: RatingBar = view.findViewById(R.id.ratingBar2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)

        view.layoutParams = layoutParams

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageURI(Uri.parse(imagesURI[position][0]))
        holder.rating.rating = imagesURI[position][1].toFloat()
        holder.image.setOnClickListener {
            if (imagesFragment.resources.configuration.orientation
                == Configuration.ORIENTATION_PORTRAIT) {
                val intent = Intent(imagesFragment.activity, ImageActivity::class.java)
                intent.putExtra("id", position.toString())
                intent.putExtra("uri", imagesURI[position][0])
                intent.putExtra("stars", imagesURI[position][1])
                imagesFragment.startActivityForResult(intent, 1)
            } else {
                (imagesFragment.fragmentManager!!.findFragmentById(R.id.fragment3) as ImageFragment)
                    .show(imagesURI[position][0], imagesURI[position][1], position.toString())
            }
        }
    }

    override fun getItemCount() : Int {
        return imagesURI.size
    }

    fun changeData(stars: String, id: Int) {
        imagesURI[id][1] = stars
        quickSort(imagesURI, 0, imagesURI.size - 1)
        notifyDataSetChanged()
    }

    private fun quickSort(array: ArrayList<ArrayList<String>>, lo: Int, hi: Int) {
        val ra = (Math.random()  * (hi - lo) + lo).toInt()
        val r = partition(array, lo, hi, ra)
        if (lo < r - 1) quickSort(array, lo, r - 1)
        if (r + 1 < hi) quickSort(array, r + 1, hi)
    }

    private fun partition(array: ArrayList<ArrayList<String>>, lo: Int, hi: Int, pivot: Int): Int {
        while (true) {
            var i = lo - 1
            var j = hi + 1
            do {
                i++
            } while (compare(array, i, pivot))
            do {
                j--
            } while (compare(array, pivot, j))
            if (i >= j) return j
            swap(array, i, j)
        }
    }

    private fun compare(array:ArrayList<ArrayList<String>>, a: Int, b: Int): Boolean {
        return array[a][1].toFloat() >= array[b][1].toFloat() && a < b
    }

    private fun swap(array: ArrayList<ArrayList<String>>, a: Int, b: Int) {
        val temp = array[a]
        array[a] = array[b]
        array[b] = temp
    }
}
