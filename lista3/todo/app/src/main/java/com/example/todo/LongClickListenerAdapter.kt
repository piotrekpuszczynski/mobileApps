package com.example.todo

import android.view.View

class LongClickListenerAdapter: View.OnLongClickListener {
    private var position: Int = 0
    private var recycler: CustomAdapter? = null
    override fun onLongClick(v: View?): Boolean {
        recycler?.let { DeleteActivityDialog(it, position).show(recycler!!.fragmentManager, DeleteActivityDialog.TAG) }
        return true
    }

    fun setPositionAndRecycler(position: Int, recycler: CustomAdapter) {
        this.position = position
        this.recycler = recycler
    }
}