package com.goncalorod.dinosaurs

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class GameActivity : AppCompatActivity() {
    companion object {
        const val WEATHER_ID = "weather_id"
    }

    var gameView : GameView? = null

    lateinit var weather : Weather

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        weather = Weather(intent.getIntExtra(WEATHER_ID, 0))

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        gameView = GameView(this, size.x, size.y)
        gameView?.weather = weather

        setContentView(gameView)
    }

    override fun onPause() {
        super.onPause()
        gameView?.pause()
    }

    override fun onResume() {
        super.onResume()
        gameView?.resume()
    }
}