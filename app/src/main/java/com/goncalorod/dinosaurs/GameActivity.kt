package com.goncalorod.dinosaurs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class GameActivity : AppCompatActivity() {
    companion object {
        const val WEATHER_ID = "weather_id"
    }

    lateinit var weather : Weather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        weather = Weather(intent.getIntExtra(WEATHER_ID, 0))
    }
}