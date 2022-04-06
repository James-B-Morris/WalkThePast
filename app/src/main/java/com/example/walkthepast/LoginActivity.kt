package com.example.walkthepast

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.hardware.input.InputManager
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
    private var currentUser = mAuth.currentUser

    // login deets
    lateinit var emailText : EditText
    lateinit var passText : EditText
    lateinit var loginBtn : Button

    // switch for making password visible or not
    private var passwordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val toolbar = findViewById<Toolbar>(R.id.loginToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        emailText = findViewById<EditText>(R.id.usernameTextBox)
        passText = findViewById<EditText>(R.id.loginPasswordTextBox)
        loginBtn = findViewById<Button>(R.id.loginBtn)
    }

    // update UI whenever the app is started
    override fun onStart() {
        super.onStart()
        update()
    }

    // func which updates the UI with login status
    fun update() {
        val currUser  = mAuth.currentUser
        val currEmail = currUser?.email

        /**
         * val greetingSpace = findViewById<TextView>(R.id.textBox)
         * if (currEmail == null) {
         *      greetingSpace.text = getString(R.string.not_logged_in)
         * }
         * else {
         *      greetingSpace.text getString(R.string. logged_in, currEmail)
         * }
         */
    }

    /**
     * checked to see if login details are valid. If so, logs in the user
     */
    fun loginClick (view: View) {
        mAuth.signInWithEmailAndPassword(
            emailText.text.toString(),
            passText.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:Success")
                    update()
                    closeKeyBoard()
                    val intent = Intent(this, MainMenuActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Log.w(TAG, "SignInWithEmail:failure", task.exception)
                    closeKeyBoard()
                    val snackbar =
                        Snackbar.make(view, getString(R.string.login_failure), Snackbar.LENGTH_LONG)
                    snackbar.show()
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