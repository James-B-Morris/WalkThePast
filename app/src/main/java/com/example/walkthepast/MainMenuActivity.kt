package com.example.walkthepast


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val toolbar = findViewById<Toolbar>(R.id.mainMenuToolBar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val view = findViewById<View>(R.id.mainMenuToolBar)

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
            R.id.settingsBtn -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * launches 'ViewTrailsActvity'
     */
    fun goToViewTrailsActivity(view:View) {
        val intent = Intent(this, ViewTrailsActivity::class.java)
        startActivity(intent)
    }

    /**
     * launches 'PointsOfInterestActivity'
     */
    fun goToPointsOfInterestActivity(view:View) {
        val intent = Intent(this, PointsOfInterestActivity::class.java)
        startActivity(intent)
    }

    /**
     * launxhes 'mapActivity'
     */
    fun goToMap(view:View) {
        val intent = Intent(this, MapActivity::class.java)
        startActivity(intent)
    }
}