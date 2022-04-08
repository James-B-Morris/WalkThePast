package com.example.walkthepast

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class PointsOfInterestAdaptor(private val poiModelArrayList: MutableList<PointsOfInterestModel>) : RecyclerView.Adapter<PointsOfInterestAdaptor.ViewHolder>() {

    /*
     * Inflate the view using the layout define in row_layout.xml
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val view = inflator.inflate(R.layout.row_layout, parent, false)
        Log.i("POINTS OF INTEREST.INFO: ", "On Create")
        
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pois = imageModelArrayList[position]
        Log.i("POINTS OF INTEREST.INFO: ", "On Bind " + images.getImageUrl())

        holder.rowText.text = pois.getName()
        //Picasso.get().load("https://images6.fanpop.com/image/photos/34300000/Kitten-cats-34352405-1600-1200.jpg").into(holder.rowImage)
        Picasso.get().load(pois.getImageUrl()).into(holder.rowImage)
    }

    /*
     * gets the number of models in the array
     */
    override fun getItemCount(): Int {
        Log.i("POINTS OF INTEREST.INFO: ", "Count")
        return poiModelArrayList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var rowImage = itemView.findViewById<View>(R.id.rowImage) as ImageView
        var rowText = itemView.findViewById<View>(R.id.rowText) as TextView
    }
}
