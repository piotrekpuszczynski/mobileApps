package com.example.todo

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class DeleteActivityDialog(customAdapter: CustomAdapter, viewType: Int) : DialogFragment() {
    private val recycler = customAdapter
    private val position = viewType

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                    .setMessage("Are you sure to delete activity?")
                    .setPositiveButton("yes") { _,_ -> recycler.delete(position) }
                    .setNegativeButton("no") { _,_ -> }
                    .create()

    companion object {
        const val TAG = "delete activity"
    }
}