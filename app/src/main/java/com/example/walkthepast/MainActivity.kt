package com.example.walkthepast

import android.content.Intent
import android.content.pm.ActivityInfo
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
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        Log.i(getString(R.string.log_main), getString(R.string.log_created))
    }

    override fun onStart() {
        super.onStart()
        Log.i(getString(R.string.log_main), getString(R.string.log_started))
        val intent = Intent(this, MainMenuActivity::class.java)

        if (currentUser != null) {
            Log.i(getString(R.string.log_main),
                (getString(R.string.log_user)
                        + currentUser!!.email.toString()
                        + getString(R.string.log_current_login)))
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(getString(R.string.log_main), getString(R.string.log_resume))
    }

    override fun onPause() {
        super.onPause()
        Log.i(getString(R.string.log_main), getString(R.string.log_pause))
    }

    override fun onStop() {
        super.onStop()
        Log.i(getString(R.string.log_main), getString(R.string.log_stop))
    }

    override  fun onDestroy() {
        super.onDestroy()
        Log.i(getString(R.string.log_main), getString(R.string.log_destroy))
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(getString(R.string.log_main), getString(R.string.log_restart))
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