package com.example.walkthepast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i("MainInfo", "in onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i("MainInfo", "in onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.i("MainInfo", "in onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("MainInfo", "in onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Main.Info", "in onStop")
    }

    override  fun onDestroy() {
        super.onDestroy()
        Log.i("MainInfo", "in onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("MainInfo", "in onRestart")
    }

    fun goToLoginActivity(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun goToRegisterActivity(view: View) {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}