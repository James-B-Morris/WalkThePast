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

class ViewTrailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_trails)

        val toolbar = findViewById<Toolbar>(R.id.viewTrailsToolBar)
        val imageModelArrayList = populateRecycler()
        val recyclerView = findViewById<View>(R.id.trailsRecyclerView) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        val adaptor = ViewTrailsAdaptor(imageModelArrayList)

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
        val view = findViewById<View>(R.id.viewTrailsToolBar)

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

    private fun populateRecycler() : ArrayList<ViewTrailsModel> {
        val list = ArrayList<ViewTrailsModel>()

        val trailImages = arrayOf(R.drawable.profile_icon, R.drawable.profile_icon, R.drawable.profile_icon) // pull images from the database
        val trailNames = arrayOf("apple", "banana", "carrot") // pull trial names from the database

        for (i in 0..(trailImages.size - 1)) {
            val trailModel = ViewTrailsModel()
            trailModel.setNames(trailNames[i])
            trailModel.setImages(trailImages[i])
            list.add(trailModel)
        }

        list.sortBy{ list -> list.modelName }
        return list
    }
}