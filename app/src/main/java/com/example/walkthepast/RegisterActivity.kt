package com.example.walkthepast

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    // authentication vars
    private var mAuth = FirebaseAuth.getInstance()

    // user info
    private val emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var emailText : EditText
    private lateinit var passText : EditText
    private lateinit var newPassText : EditText
    private lateinit var registerBtn : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val toolbar = findViewById<Toolbar>(R.id.registerToolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        emailText = findViewById(R.id.registerEmailTxtBx)
        passText = findViewById(R.id.registerNewPasswordTxtBx)
        newPassText = findViewById(R.id.registerConfimPasswordTxtBx)
        registerBtn = findViewById(R.id.registerCreateAccountBtn)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.layout_loggedout_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun registerClick(view : View) {
        val email = emailText.text.toString().trim()
        val pass = passText.text.toString()
        val newPass = newPassText.text.toString()

        val isEmailEmpty = emailText.text.isEmpty()
        val isPassEmpty = passText.text.isEmpty()
        val isNewPassEmpty = newPassText.text.isEmpty()
        val isMatch = pass.contentEquals(newPass)
        val isEmailValid = email.matches(emailPattern.toRegex())


        if ((isEmailEmpty) || (isPassEmpty) || (isNewPassEmpty)) {
            displayMessage(registerBtn, getString(R.string.register_empty_field))
        }
        else if (!isEmailValid) {
            displayMessage(registerBtn, getString(R.string.register_invalid_email))
        }
        else if (pass.length < 8) {
            displayMessage(registerBtn, getString(R.string.register_too_short))
        }
        else if (!isMatch) {
            displayMessage(registerBtn, getString(R.string.register_no_match))
        }
        else {
            createAccount(email, pass)
        }
    }

    private fun createAccount(email : String, password : String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(getString(R.string.log_register),
                        getString(R.string.log_register_success))
                    closeKeyBoard()
                    val intent = Intent(this, MainMenuActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(getString(R.string.log_register),
                        getString(R.string.log_register_failure), task.exception)
                    closeKeyBoard()
                    displayMessage(registerBtn, getString(R.string.register_already_exists))
                }
            }
    }

    fun goToLoginActivity(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
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