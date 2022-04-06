package com.example.walkthepast

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar

/**
 * MAKE SURE TO IMPLEMENT PREFERENCES
 */
class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<Toolbar>(R.id.settingsToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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
                val snackbar =
                    Snackbar.make(view, getString(R.string.xml_logout), Snackbar.LENGTH_LONG)
                snackbar.show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}