package com.example.data3740.project.landing

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.data3740.R

import android.content.Intent
import android.widget.Button
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import android.media.MediaPlayer
import android.util.Log
import android.view.WindowManager
class LoginActivity : AppCompatActivity() {

    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var googleSignInOptions: GoogleSignInOptions
    val RC_SIGN_IN: Int = 9001
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        if(FirebaseAuth.getInstance().currentUser != null){
            val menu = Intent(this, SelectImageActivity::class.java)
            startActivity(menu)
            finish()
        }
        //gives error on judit
        val btnLogin = findViewById<View>(R.id.btn_login) as SignInButton
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("786361250340-goq9v51lh3e81npe8ckj8itaccokkmaj.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
        btnLogin.setOnClickListener {
            signIn()

        }





    }
    private fun signIn(){
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this,"Error 1", Toast.LENGTH_SHORT).show()
                // ...
            }
        }
    }
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = auth.currentUser
                val userstring = user?.email.toString()
                Toast.makeText(this,userstring, Toast.LENGTH_SHORT).show()

                val menu = Intent(this, SelectImageActivity::class.java)
                startActivity(menu)
                finish()
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(this,"Error 2", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
