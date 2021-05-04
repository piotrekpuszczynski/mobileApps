package com.example.images

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ImageFragment : Fragment() {

    private var imageID: String = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val i = inflater.inflate(R.layout.fragment_image, container, false)
        i.findViewById<Button>(R.id.cancel).setOnClickListener {
            if (resources.configuration.orientation
                == Configuration.ORIENTATION_PORTRAIT) {
                activity?.finish()
            } else {
                view!!.findViewById<ImageView>(R.id.imageView2).setImageResource(0)
                view!!.findViewById<RatingBar>(R.id.ratingBar).rating = 0f
            }
        }
        i.findViewById<Button>(R.id.confirm).setOnClickListener {
            if (resources.configuration.orientation
                == Configuration.ORIENTATION_PORTRAIT) {
                val myIntent = Intent()
                myIntent.putExtra("id", imageID)
                myIntent.putExtra("stars", view!!.findViewById<RatingBar>(R.id.ratingBar).rating.toString())
                activity?.setResult(AppCompatActivity.RESULT_OK, myIntent)
                activity?.finish()
            } else {
                if (imageID != "") {
                    val adapter = activity?.findViewById<RecyclerView>(R.id.images)!!.adapter as ImagesRecycler
                    adapter.changeData(view!!.findViewById<RatingBar>(R.id.ratingBar).rating.toString(), imageID.toInt())
                    view!!.findViewById<ImageView>(R.id.imageView2).setImageResource(0)
                    view!!.findViewById<RatingBar>(R.id.ratingBar).rating = 0f
                    imageID = ""
                }
            }
        }
        return i
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity?.intent != null) {
            imageID = activity!!.intent.getStringExtra("id").toString()
            val uri = activity!!.intent.getStringExtra("uri")
            val rating = activity!!.intent.getStringExtra("stars")
            show(uri, rating, imageID)
        }
    }

    fun show(uri: String?, stars: String?, id: String?) {
        if (uri != null && stars != null && id != null) {
            imageID = id
            view!!.findViewById<ImageView>(R.id.imageView2).setImageURI(Uri.parse(uri))
            view!!.findViewById<RatingBar>(R.id.ratingBar).rating = stars.toFloat()
        }
    }
}