package com.goncalorod.dinosaurs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var user : FirebaseUser

    private lateinit var playButton         : Button
    private lateinit var leaderboardButton  : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        user = auth.currentUser!!

        /* TODO:
            Get current weather from API and lock play button while weather is being downloaded
         */

        playButton = findViewById(R.id.button_Play)
        playButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        leaderboardButton = findViewById(R.id.button_Leaderboard)
        leaderboardButton.setOnClickListener {
            val intent = Intent(this, LeaderboardActivity::class.java)
            startActivity(intent)
        }

        // Cloud firestore example
        /*
        val db = Firebase.firestore

        val message = hashMapOf(
            "message" to "Hello!"
        )

        db.collection("messages").add(message)
        */
    }
}