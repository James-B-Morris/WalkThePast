package com.example.walkthepast

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ViewTrailsAdaptor(private val imageModelArrayList: MutableList<ViewTrailsModel>) : RecyclerView.Adapter<ViewTrailsAdaptor.ViewHolder>() {

    /*
     * Inflate the view using the layout define in row_layout.xml
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.row_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = imageModelArrayList[position]

        holder.imgView.setImageResource(info.getImages())
        holder.txtMsg.text = info.getNames()
    }

    /*
     * gets the number of models in the array
     */
    override fun getItemCount(): Int {
        return imageModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgView = itemView.findViewById<View>(R.id.rowImage) as ImageView
        var txtMsg = itemView.findViewById<View>(R.id.rowText) as TextView
    }
}