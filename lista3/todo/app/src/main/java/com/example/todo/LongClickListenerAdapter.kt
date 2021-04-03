package com.example.todo

import android.view.View

class LongClickListenerAdapter: View.OnLongClickListener {
    private var position: Int = 0
    private var recycler: CustomAdapter? = null
    override fun onLongClick(v: View?): Boolean {
        recycler?.let { DeleteTaskDialog(it, position).show(recycler!!.fragmentManager, DeleteTaskDialog.TAG) }
        return true
    }

    fun setPositionAndRecycler(position: Int, recycler: CustomAdapter) {
        this.position = position
        this.recycler = recycler
    }
}