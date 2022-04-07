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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    // authentication vars
    private var mAuth = FirebaseAuth.getInstance()

    // datastorage
    private val db = Firebase.firestore

    // user info
    private val emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var usernameText : EditText
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

        usernameText = findViewById(R.id.registerUsernameTxtBx)
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
        val username = usernameText.text.toString().trim()
        val email = emailText.text.toString().trim()
        val pass = passText.text.toString()
        val newPass = newPassText.text.toString()

        val isUsernameEmpty = usernameText.text.isEmpty()
        val isEmailEmpty = emailText.text.isEmpty()
        val isPassEmpty = passText.text.isEmpty()
        val isNewPassEmpty = newPassText.text.isEmpty()
        val isUsernameValid = ((3 <= username.length) && (username.length <= 12))
        val isEmailValid = email.matches(emailPattern.toRegex())
        val isMatch = pass.contentEquals(newPass)



        if ((isUsernameEmpty) || (isEmailEmpty) || (isPassEmpty) || (isNewPassEmpty)) {
            displayMessage(registerBtn, getString(R.string.register_empty_field))
        }
        else if (!isUsernameValid) {
            displayMessage(registerBtn, getString(R.string.register_invalid_username))
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
            createAccount(username, email, pass)
        }
    }

    private fun createAccount(username : String, email : String, password : String) {
        val newUser = hashMapOf(
            "admin" to false,
            "username" to username,
            "email" to email,
            "visited" to {}
        )

        // save user's authentication details
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(getString(R.string.log_register),
                        getString(R.string.log_register_success))

                    // add user to the database
                    db.collection("Users")
                        .document(email)
                        .set(newUser)
                        .addOnSuccessListener {
                            Log.d(getString(R.string.log_register),
                                getString(R.string.log_register_user_success) + email)

                            closeKeyBoard()
                            val intent = Intent(this, MainMenuActivity::class.java)
                            startActivity(intent)
                        }
                        .addOnFailureListener { e ->
                            Log.w(getString(R.string.log_register),
                                getString(R.string.log_register_user_failure), e)
                        }
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