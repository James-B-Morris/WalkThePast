package com.example.walkthepast

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PointsOfInterestActivity :AppCompatActivity() {
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_points_of_interest)
        Log.i(getString(R.string.log_points_of_interest), getString(R.string.log_created))

        initToolbar()
        initViews()
    }

    private fun initToolbar(){
        val toolbar = findViewById<Toolbar>(R.id.poisToolBar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViews() {
        val recyclerView = findViewById<View>(R.id.poisRecyclerView) as RecyclerView
        val layoutManager = LinearLayoutManager(this)
        val pois = populateRecycler()
        val adapter = PointsOfInterestAdaptor(pois)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
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


    /**
     * temporary population method, will be replaced once the new one is finished
     */
    private fun populateRecycler() : ArrayList<PointsOfInterestModel> {
        val pois = ArrayList<PointsOfInterestModel>()

        val poiImageUrls = arrayOf(
            "https://firebasestorage.googleapis.com/v0/b/walk-the-past.appspot.com/o/elysium_studio_and_gallery.png?alt=media&token=406621e6-99d9-4187-853b-4d64bce2f0c1",
            "https://firebasestorage.googleapis.com/v0/b/walk-the-past.appspot.com/o/glynn_vivian_art.png?alt=media&token=f125b872-b4eb-4453-b593-76d564872ff8",
            "https://firebasestorage.googleapis.com/v0/b/walk-the-past.appspot.com/o/joes_icecream.png?alt=media&token=8f89bcb5-01dc-45ea-8f3d-0da6a773111f",
            "https://firebasestorage.googleapis.com/v0/b/walk-the-past.appspot.com/o/old_swansea.png?alt=media&token=d5122dc6-79a1-4940-ac42-1f0267799d55",
            "https://firebasestorage.googleapis.com/v0/b/walk-the-past.appspot.com/o/palace_theatre.png?alt=media&token=d75d8f6e-7c83-4836-91c1-c9b7de0d6813",
            "https://firebasestorage.googleapis.com/v0/b/walk-the-past.appspot.com/o/plantasia.png?alt=media&token=e8ce8024-e36a-4cea-a4d4-1784652b1bff",
            "https://firebasestorage.googleapis.com/v0/b/walk-the-past.appspot.com/o/swanea_central_library.png?alt=media&token=97ef5b88-c878-4f3e-8702-1d5de1a3d2e6",
            "https://firebasestorage.googleapis.com/v0/b/walk-the-past.appspot.com/o/swansea_arena.png?alt=media&token=8aded1e7-a7ea-479f-92c7-c994ad09f304",
            "https://firebasestorage.googleapis.com/v0/b/walk-the-past.appspot.com/o/swansea_castle.png?alt=media&token=63405e9d-d295-4c99-9ab7-5ccf3e2a1ddf",
            "https://firebasestorage.googleapis.com/v0/b/walk-the-past.appspot.com/o/swansea_museum.png?alt=media&token=491606b0-9f92-4a13-8cfc-629cb969b57e"
        ) // pull images from the database

        val poiNames = arrayOf(
            "Elysium Studio & Gallery",
            "glynn Vivian Art Gallery",
            "Joe's Ice Cream Parlour",
            "1940s Swansea",
            "Palace Theatre",
            "Plantasia Tropical Zoo",
            "Swansea Central Library",
            "Swansea Arena",
            "Swansea Castle",
            "Swansea Museum"
        ) //

        for (i in poiImageUrls.indices) {
            val trailModel = PointsOfInterestModel()
            trailModel.setName(poiNames[i])
            trailModel.setImageUrl(poiImageUrls[i])
            pois.add(trailModel)
        }

        pois.sortBy{ pois -> pois.modelName }
        return pois
    }

    private fun populateRecyclerNew() : ArrayList<PointsOfInterestModel> {
        val pois = ArrayList<PointsOfInterestModel>()
        val newPoi = PointsOfInterestModel()

        db.collection(getString(R.string.path_points_of_interest))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(getString(R.string.log_points_of_interest),
                        "${document.id} => ${document.get(getString(R.string.db_poi_image))}")

                    newPoi.setName(document.get(getString(R.string.db_poi_title)) as String)
                    newPoi.setImageUrl(document.get(getString(R.string.db_poi_image)) as String)

                    pois.add(newPoi)
                    Log.d(getString(R.string.log_points_of_interest),
                        pois[document.id.toInt()].getImageUrl() as String)
                }
            }
            .addOnFailureListener {
                Log.w(getString(R.string.log_points_of_interest),
                    getString(R.string.log_document_error))
            }

        Log.d(getString(R.string.log_points_of_interest), getString(R.string.log_return))
        pois.sortBy{ pois -> pois.modelName }
        return pois
    }
}