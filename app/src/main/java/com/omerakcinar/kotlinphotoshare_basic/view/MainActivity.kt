package com.omerakcinar.kotlinphotoshare_basic.view

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.omerakcinar.kotlinphotoshare_basic.R
import com.omerakcinar.kotlinphotoshare_basic.adapter.FeedRecyclerAdapter
import com.omerakcinar.kotlinphotoshare_basic.databinding.ActivityMainBinding
import com.omerakcinar.kotlinphotoshare_basic.model.Post

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var postArrayList : ArrayList<Post>
    private lateinit var postAdapter : FeedRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.homeIcon.setImageResource(R.drawable.home_icon_pressed)
        auth = Firebase.auth
        db = Firebase.firestore
        postArrayList = ArrayList<Post>()
        getData()
        binding.feedRecyclerView.layoutManager = LinearLayoutManager(this)
        postAdapter = FeedRecyclerAdapter(postArrayList)
        binding.feedRecyclerView.adapter = postAdapter
    }

    private fun getData(){
        db.collection("Posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error != null){
                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_LONG).show()
            }else{
                if (value != null){
                    if (!value.isEmpty){
                        val documents = value.documents
                        for (document in documents){
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String

                            val post = Post(userEmail,comment,downloadUrl)
                            postArrayList.add(post)
                        }
                        postAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    fun homeIconClicked(view: View){
        binding.homeIcon.setImageResource(R.drawable.home_icon_pressed)
        binding.addIcon.setImageResource(R.drawable.add_icon)
        binding.profileIcon.setImageResource(R.drawable.profile_icon)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    fun addIconClicked(view: View) {
        binding.homeIcon.setImageResource(R.drawable.home_icon)
        binding.addIcon.setImageResource(R.drawable.add_icon_pressed)
        binding.profileIcon.setImageResource(R.drawable.profile_icon)

        val intent = Intent(this, PhotoUploadActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    fun profileIconClicked(view: View) {
        binding.homeIcon.setImageResource(R.drawable.home_icon)
        binding.addIcon.setImageResource(R.drawable.add_icon)
        binding.profileIcon.setImageResource(R.drawable.profile_icon_pressed)
    }

    fun logOutClicked(view: View) {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Log out")
        alert.setMessage("Log out?")
        alert.setPositiveButton("Yes",DialogInterface.OnClickListener { dialogInterface, i ->
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })
        alert.setNegativeButton("Cancel",DialogInterface.OnClickListener { dialogInterface, i ->  })
        alert.show()
    }
}