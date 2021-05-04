package com.example.images

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ImagesFragment : Fragment() {

    private val imagesURI = arrayOf (
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5,
        R.drawable.img6,
        R.drawable.img7,
        R.drawable.img8,
        R.drawable.img9,
        R.drawable.img10,
        R.drawable.img11,
        R.drawable.img12,
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5,
        R.drawable.img6,
        R.drawable.img7,
        R.drawable.img8,
        R.drawable.img9,
        R.drawable.img10,
        R.drawable.img11,
        R.drawable.img12
    )

    private var columns = 2
    private val array: ArrayList<ArrayList<String>> = getArray()
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        sharedPreferences = activity!!.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        for (i in 0 until sharedPreferences.getInt("number", 0)) {
            array[i][1] = sharedPreferences.getString("stars$i", null)!!
            array[i][0] = sharedPreferences.getString("image$i", null)!!
        }

        val view = inflater.inflate(R.layout.fragment_images, container, false)
        val list = view.findViewById<RecyclerView>(R.id.images)
        view.post {
            list.adapter = ImagesRecycler(array, columns, view.width, this)
            list.layoutManager = GridLayoutManager(view.context, columns)
        }
        view.findViewById<Button>(R.id.button).setOnClickListener {
            columns++
            if (columns == 5) columns = 1
            view.post {
                list.adapter = ImagesRecycler(array, columns, view.width, this)
                list.layoutManager = GridLayoutManager(view.context, columns)
            }
        }
        return view
    }

    override fun onPause() {
        super.onPause()
        save()
    }

    override fun onStop() {
        super.onStop()
        save()
    }

    private fun save() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()

        for (i in 0 until array.size) {
            editor.putString("stars$i", array[i][1])
            editor.putString("image$i", array[i][0])
        }

        editor.putInt("number", array.size)
        editor.apply()
    }

    private fun getArray(): ArrayList<ArrayList<String>> {
        val array: ArrayList<ArrayList<String>> = ArrayList()
        var a: ArrayList<String>
        for (i in imagesURI.indices) {
            a = ArrayList()
            a.add("android.resource://" + BuildConfig.APPLICATION_ID + "/" + imagesURI[i])
            a.add("0.0")

            array.add(a)
        }

        return array
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            val adapter = activity?.findViewById<RecyclerView>(R.id.images)!!.adapter as ImagesRecycler
            adapter.changeData(data?.getStringExtra("stars").toString(), data?.getStringExtra("id")!!.toInt())
        }
    }
}