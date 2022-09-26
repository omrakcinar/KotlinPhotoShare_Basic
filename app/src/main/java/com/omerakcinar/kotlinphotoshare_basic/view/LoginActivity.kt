package com.omerakcinar.kotlinphotoshare_basic.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.omerakcinar.kotlinphotoshare_basic.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        //check current user if signed in
        val currentUser = auth.currentUser
        if (currentUser != null){
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    fun loginClicked(view: View) {
        val userEmail = binding.emailTextView.text.toString()
        val userPassword = binding.passwordTextView.text.toString()

        if (userEmail.isEmpty() || userPassword.isEmpty()){
            Toast.makeText(this,"Please enter your email and password.",Toast.LENGTH_LONG).show()
        }else {
            auth.signInWithEmailAndPassword(userEmail,userPassword).addOnSuccessListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this,"Login success",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signUpClicked(view: View) {
        val newUserEmail = binding.emailTextView.text.toString()
        val newUserPassword = binding.passwordTextView.text.toString()

        if (newUserEmail.isEmpty() || newUserPassword.isEmpty()){
            Toast.makeText(this,"Please enter your email and password.",Toast.LENGTH_LONG).show()
        } else {
            auth.createUserWithEmailAndPassword(newUserEmail,newUserPassword).addOnSuccessListener {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this,"Signed up. Logging in...",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }
    }
}