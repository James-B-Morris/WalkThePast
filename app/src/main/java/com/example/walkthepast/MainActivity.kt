package com.example.walkthepast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    // Authentication Vars
    private var mAuth = FirebaseAuth.getInstance()
    private var currentUser = mAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(getString(R.string.log_main), "in onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.i(getString(R.string.log_main), "in onStart")
        val intent = Intent(this, MainMenuActivity::class.java)

        if (currentUser != null) {
            Log.i("MainInfo",
                ("User " + currentUser!!.email.toString() +  " currently logged in"))
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(getString(R.string.log_main), "in onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(getString(R.string.log_main), "in onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i(getString(R.string.log_main), "in onStop")
    }

    override  fun onDestroy() {
        super.onDestroy()
        Log.i(getString(R.string.log_main), "in onDestroy")
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