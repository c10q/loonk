package com.example.tutorial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_main.*

private const val RC_SIGN_IN = 9001
private const val TAG = "android"

private var googleSignInClient: GoogleSignInClient? = null

var auth: FirebaseAuth? = null

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

        login_button.setOnClickListener() {
            loginWithEmail()
        }

        google_login.setOnClickListener() {
            loginWithGoogle()
        }

        register_button.setOnClickListener() {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }


    private fun intentHome(){
        val intent = Intent(this, HomeActivity::class.java)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)
    }

    private fun loginWithGoogle() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth!!.signInWithCredential(credential)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth?.currentUser
                    Toast.makeText(this.applicationContext, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    intentHome()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", it.exception)
                    Toast.makeText(this.applicationContext, "인증 실패..", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun loginWithEmail() {
        if(!checkEmail(login_email.text.toString())){
            Toast.makeText(this.applicationContext, "이메일 형식을 확인해주세요..", Toast.LENGTH_SHORT).show()
        } else if(login_password.text.isBlank()){
            Toast.makeText(this.applicationContext, "패스워드를 입력해주세요..", Toast.LENGTH_SHORT).show()
        } else {
            auth!!.signInWithEmailAndPassword(login_email.text.toString(), login_password.text.toString())
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        val user = auth?.currentUser
                        Toast.makeText(this.applicationContext, "로그인 성공!", Toast.LENGTH_SHORT).show()
                        intentHome()
                    } else {
                        Toast.makeText(this.applicationContext, "로그인 실패..", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    private fun checkEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}



