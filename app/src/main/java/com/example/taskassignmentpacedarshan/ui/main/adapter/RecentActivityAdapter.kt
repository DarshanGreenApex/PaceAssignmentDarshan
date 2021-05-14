package com.example.taskassignmentpacedarshan.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskassignmentpacedarshan.R

class RecentActivityAdapter() :
    RecyclerView.Adapter<RecentActivityAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recent_activity_list, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems()
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return 2
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var recyclerView : RecyclerView
        fun bindItems() {
            recyclerView = itemView.findViewById(R.id.recyclerRecentActivityItems)
            val adapter = RecentActivityItemAdapter()
            recyclerView.isNestedScrollingEnabled = false
            recyclerView.layoutManager =
                LinearLayoutManager(itemView.context, RecyclerView.VERTICAL, false)
            recyclerView.adapter = adapter
        }
    }
}