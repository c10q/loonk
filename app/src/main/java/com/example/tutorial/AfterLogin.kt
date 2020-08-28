package com.example.tutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.after_login.*

class AfterLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.after_login)

        btn_logout.setOnClickListener() {
            FirebaseAuth.getInstance().signOut();
            setContentView(R.layout.activity_main)
        }

    }
}