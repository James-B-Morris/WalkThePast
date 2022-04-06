package com.example.walkthepast

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class PointsOfInterestActivity :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points_of_interest)

        val toolbar = findViewById<Toolbar>(R.id.poisToolBar)
        val imageModelArrayList = populateRecycler()
        val recyclerView = findViewById<View>(R.id.poisRecyclerView) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        val adaptor = PointsOfInterestAdaptor(imageModelArrayList)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adaptor
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val view = findViewById<View>(R.id.poisToolBar)

        when (item.itemId) {
            R.id.refresh -> {
                val sb = Snackbar.make(view, getString(R.string.xml_refresh), Snackbar.LENGTH_LONG)
                sb.show()
                return true
            }
            R.id.action_logout -> {
                val snackbar =
                    Snackbar.make(view, getString(R.string.xml_logout), Snackbar.LENGTH_LONG)
                snackbar.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun populateRecycler() : ArrayList<PointsOfInterestModel> {
        val list = ArrayList<PointsOfInterestModel>()

        val trailImages = arrayOf(R.drawable.swansea_museum, R.drawable.swansea_castle,
            R.drawable.old_swansea, R.drawable.plantasia, R.drawable.glynn_vivian_art,
            R.drawable.palace_theatre) // pull images from the database
        val trailNames = arrayOf("Swansea Museum", "Swansea Castle", "1940s Swansea",
            "Plantasia Tropical zoo", "Glynn Vivian Art Gallery", "Palace theatre") // pull trial names from the database

        for (i in 0..(trailImages.size - 1)) {
            val trailModel = PointsOfInterestModel()
            trailModel.setNames(trailNames[i])
            trailModel.setImages(trailImages[i])
            list.add(trailModel)
        }

        list.sortBy{ list -> list.modelName }
        return list
    }
}