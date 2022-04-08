package com.example.walkthepast

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

/**
 * MAKE SURE TO IMPLEMENT PREFERENCES
 */
class SettingsActivity : AppCompatActivity() {
    private var mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initToolbar()
        initProfile()
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.settingsToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initProfile() {
        val username = findViewById<TextView>(R.id.settingsUsernameTxtBx)
        val profileIcon = findViewById<ImageView>(R.id.settingsProfileIcon)
        Picasso.get().load(getString(R.string.err_kitten)).into(profileIcon)

        Log.d(getString(R.string.log_settings), currentUser?.email as String)
        db.collection(getString(R.string.path_users))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.get(getString(R.string.db_users_email)) == currentUser?.email) {
                        username.text = document.get(getString(R.string.db_users_name)) as String
                    }
                    else {
                        username.text = getString(R.string.err_user_not_found)
                    }
                }
            }
            .addOnFailureListener {
                Log.w(getString(R.string.log_points_of_interest),
                    getString(R.string.log_document_error))
                username.text = getString(R.string.err_user_not_found)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val view = findViewById<View>(R.id.settingsToolBar)

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
        }
        return super.onOptionsItemSelected(item)
    }
}