package com.goncalorod.dinosaurs

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var user : FirebaseUser

    private lateinit var playButton         : Button
    private lateinit var leaderboardButton  : Button

    private var location : Location? = null
    private lateinit var weather : Weather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
        user = auth.currentUser!!

        /* TODO:
            Get current weather from API and lock play button while weather is being downloaded
         */

        getLocation()

        playButton = findViewById(R.id.button_Play)
        playButton.setOnClickListener {

            GlobalScope.async {
                var weather = Weather.GetWeatherFromGPS(location)

                val intent = Intent(this@MainActivity, GameActivity::class.java)
                intent.putExtra(GameActivity.WEATHER_ID, weather.conditionID)
                startActivity(intent)
            }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permissions.get(0)
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                getLocation()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            1
        )
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission()
        } else {
            LocationServices.getFusedLocationProviderClient(this).lastLocation.addOnSuccessListener { loc: Location? ->
                location = loc
            }
        }
    }
}