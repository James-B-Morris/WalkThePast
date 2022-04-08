package com.example.walkthepast

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    // Authentication Vars
    private var mAuth = FirebaseAuth.getInstance()
    private var currUser = mAuth.currentUser

    // login deets
    private val emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var emailText : EditText
    private lateinit var passText : EditText
    private lateinit var loginBtn : Button

    // switch for making password visible or not
    private var passwordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val toolbar = findViewById<Toolbar>(R.id.loginToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        emailText = findViewById(R.id.usernameTextBox)
        passText = findViewById(R.id.loginPasswordTextBox)
        loginBtn = findViewById(R.id.loginBtn)
    }

    /**
     * checked to see if login details are valid. If so, logs in the user
     */
    fun loginClick (view: View) {
        val email = emailText.text.toString().trim()
        val pass = passText.text.toString()

        val isEmailEmpty = emailText.text.isEmpty()
        val isPassEmpty = passText.text.isEmpty()
        val isEmailValid = email.matches(emailPattern.toRegex())


        if ((isEmailEmpty) || (isPassEmpty)) {
            displayMessage(loginBtn, getString(R.string.login_missing))
        }
        else if (!isEmailValid) {
            displayMessage(loginBtn, getString(R.string.register_invalid_email))
        }
        else {
            login(email, pass)
        }
    }

    private fun login(email : String, password : String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:Success")
                    closeKeyBoard()
                    val intent = Intent(this, MainMenuActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Log.w(TAG, "SignInWithEmail:failure", task.exception)
                    closeKeyBoard()
                    displayMessage(loginBtn, getString(R.string.login_failure))
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_loggedout_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun goToMainMenuActivity(view: View) {
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }

    fun goToResetLoginActivity(view: View) {
        val intent = Intent(this, LoginResetActivity::class.java)
        startActivity(intent)
    }

    fun showPasswordBtnClicked(view:View) {
        val loginBtn = findViewById<TextView>(R.id.loginPasswordTextBox)
        if (passwordVisible) {
            passwordVisible = false
            loginBtn.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        else{
            passwordVisible = true
            loginBtn.inputType = InputType.TYPE_CLASS_TEXT
        }
    }

    fun displayMessage(view : View, msg : String) {
        val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        snackbar.show()
    }

    /**
     * helper function to close keyboard
     */
    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}