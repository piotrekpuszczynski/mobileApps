package com.example.todo

import android.view.View

class LongClickListenerAdapter: View.OnLongClickListener {
    private var holder: CustomAdapter.ViewHolder? = null
    private var recycler: CustomAdapter? = null
    override fun onLongClick(v: View?): Boolean {
        recycler?.let { holder?.adapterPosition?.let { it1 -> DeleteActivityDialog(it, it1).show(recycler!!.fragmentManager, DeleteActivityDialog.TAG) } }
        return true
    }

    fun setPositionAndRecycler(holder: CustomAdapter.ViewHolder, recycler: CustomAdapter) {
        this.holder = holder
        this.recycler = recycler
    }
}