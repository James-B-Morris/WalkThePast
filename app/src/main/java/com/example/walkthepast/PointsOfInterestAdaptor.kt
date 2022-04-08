package com.example.walkthepast

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PointsOfInterestAdaptor(private val poiModelArrayList: MutableList<PointsOfInterestModel>) : RecyclerView.Adapter<PointsOfInterestAdaptor.ViewHolder>() {

    /*
     * Inflate the view using the layout define in row_layout.xml
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.row_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pois = poiModelArrayList[position]

        Log.i("POIS: ", "On Bind")
        holder.rowImage.setImageResource(pois.getImages())
        holder.rowText.text = pois.getNames()
    }

    /*
     * gets the number of models in the array
     */
    override fun getItemCount(): Int {
        return poiModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rowImage = itemView.findViewById<View>(R.id.rowImage) as ImageView
        var rowText = itemView.findViewById<View>(R.id.rowText) as TextView
    }
}