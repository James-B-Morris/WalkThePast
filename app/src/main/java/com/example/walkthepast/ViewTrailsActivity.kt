package com.example.walkthepast

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

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
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.profile -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.settingsBtn -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun populateRecycler() : ArrayList<ViewTrailsModel> {
        val list = ArrayList<ViewTrailsModel>()

        val trailImages = arrayOf(
            R.drawable.historical,
            R.drawable.entertainment,
            R.drawable.adventure) // pull images from the database
        val trailNames = arrayOf(
            getString(R.string.trail_historical),
            getString(R.string.trail_entertaining),
            getString(R.string.trail_adventurous)) // pull trial names from the database

        for (i in trailImages.indices) {
            val trailModel = ViewTrailsModel()
            trailModel.setNames(trailNames[i])
            trailModel.setImages(trailImages[i])
            list.add(trailModel)
        }

        list.sortBy{ list -> list.modelName }
        return list
    }
}