package com.example.tutorial

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.register.*

val firebaseDatabase = FirebaseDatabase.getInstance()
val dbRef = firebaseDatabase.getReference("users")

data class User(
    var username: String? = "",
    var email: String? = "",
    var accountType: String? = ""
)

val user = auth?.currentUser!!


class Register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        confirm_register.setOnClickListener() {
            createEmail()
            writeNewUser(register_nickname.text.toString(), register_email.text.toString())
        }

        setSpinner()

    }

    private fun writeNewUser(name: String, email: String?, accType: String = "Email" ) {
        val user = UserInfo(name, email, accType)
        dbRef.push().setValue(user)
    }

    private fun setSpinner() {
        val regionList = resources.getStringArray(R.array.spinner_region)

        val arrayAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            regionList
        )

        spinner_region.adapter = arrayAdapter
        spinner_region.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
                Toast.makeText(applicationContext, "선택완료!.", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(adapterView: AdapterView<*>) {}
        }

    }

    private fun createEmail() {
        auth!!.createUserWithEmailAndPassword(register_email.text.toString(), register_password.text.toString())
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    auth?.currentUser?.sendEmailVerification()?.addOnCompleteListener{ vert ->
                        if(vert.isSuccessful){
                            Snackbar.make(view, "인증이 완료됐습니다.", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Snackbar.make(view, "오류가 생겼습니다", Snackbar.LENGTH_SHORT).show()
                }
            }
    }
}