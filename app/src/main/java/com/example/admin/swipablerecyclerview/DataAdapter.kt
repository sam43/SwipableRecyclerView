package com.example.admin.swipablerecyclerview

/**
 * Created by ADMIN on 5/26/2017.
 */

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

internal class DataAdapter(private val names: ArrayList<String>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): DataAdapter.ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.row_layout, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {

        viewHolder.tv_names.text = names[i]
    }

    override fun getItemCount(): Int {
        return names.size
    }

    fun addItem(country: String) {
        names.add(country)
        notifyItemInserted(names.size)
    }

    fun removeItem(position: Int) {
        names.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, names.size)
    }

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tv_names: TextView

        init {

            tv_names = view.findViewById(R.id.tv_names) as TextView
        }
    }
}