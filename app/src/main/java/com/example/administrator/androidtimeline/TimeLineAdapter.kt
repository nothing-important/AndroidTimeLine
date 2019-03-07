package com.example.administrator.androidtimeline

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TimeLineAdapter(var context: Context , var list: List<TimeLineBean>) : RecyclerView.Adapter<TimeLineAdapter.TimeLineAdapter_VH>() {

    var layoutInflater : LayoutInflater? = null

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TimeLineAdapter_VH {
        var view = layoutInflater?.inflate(R.layout.item_time_line_view , p0 , false)
        return TimeLineAdapter_VH(view!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TimeLineAdapter_VH, psn: Int) {
        holder.item_desc.text = list[psn].desc
    }


    class TimeLineAdapter_VH(view : View) : RecyclerView.ViewHolder(view){
        var item_desc : TextView = view.findViewById(R.id.item_desc)
    }
}