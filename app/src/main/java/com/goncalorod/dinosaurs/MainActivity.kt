package com.goncalorod.dinosaurs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cloud firestore example
        val db = Firebase.firestore

        val message = hashMapOf(
            "message" to "Hello!"
        )

        db.collection("messages").add(message)
    }
}