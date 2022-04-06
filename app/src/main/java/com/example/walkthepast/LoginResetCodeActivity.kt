package com.example.walkthepast

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class LoginResetCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_login_code)

        val toolbar = findViewById<Toolbar>(R.id.resetLoginCodeToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_loggedout_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun goToResetLoginActivity(view: View) {
        val intent = Intent(this, LoginResetActivity::class.java)
        startActivity(intent)
    }
}