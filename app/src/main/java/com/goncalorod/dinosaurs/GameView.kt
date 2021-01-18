package com.goncalorod.dinosaurs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.annotation.RequiresApi

class GameView : SurfaceView, Runnable {

    private var weather : Weather? = null

    private var playing = false
    private var gameThread: Thread? = null

    private var surfaceHolder: SurfaceHolder? = null
    private var canvas: Canvas? = null

    private var paint: Paint = Paint()

    private var screenWidth = 0
    private var screenHeight = 0

    private lateinit var player: Player
    private lateinit var cactus: Cactus

    private var sun: Sun? = null
    private var clouds: Clouds? = null

    private fun init(context: Context?, width: Int, height: Int, weather: Weather?){
        surfaceHolder = holder

        this.weather = weather

        screenWidth = width
        screenHeight = height

        player = Player(context, screenWidth, screenHeight)
        cactus = Cactus(context, screenWidth, screenHeight)

        var condition = weather?.condition
        //var condition = WeatherCondition.CLOUDS
        //var condition = WeatherCondition.THUNDERSTORM

        when (condition) {
            WeatherCondition.THUNDERSTORM -> {
                clouds = Clouds(context, screenWidth, screenHeight)
                // TODO: Thunder
                // TODO: Heavy rain
            }
            WeatherCondition.DRIZZLE -> {
                clouds = Clouds(context, screenWidth, screenHeight)
                // TODO: Light rain
            }
            WeatherCondition.RAIN -> {
                clouds = Clouds(context, screenWidth, screenHeight)
                // TODO: Heavy rain
            }
            WeatherCondition.SNOW -> {
                clouds = Clouds(context, screenWidth, screenHeight)
                // TODO: Snow
            }
            WeatherCondition.ATMOSPHERE -> {
                clouds = Clouds(context, screenWidth, screenHeight)
            }
            WeatherCondition.CLEAR -> {
                sun = Sun(context, screenWidth, screenHeight)
            }
            WeatherCondition.CLOUDS -> {
                sun = Sun(context, screenWidth, screenHeight)
                clouds = Clouds(context, screenWidth, screenHeight)
            }
        }
    }

    constructor(context: Context?, width: Int, height: Int, weather: Weather?) : super(context){
        init(context, width, height, weather)
    }
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context, 0, 0, null)
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
    ){
        init(context, 0, 0, null)
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
            context: Context?,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes){
        init(context, 0, 0, null)
    }

    override fun run() {
        while (playing){
            update()
            draw()
            control()
        }
    }

    private fun update(){
        player.update()
        cactus.update()

        clouds?.update()

        if (Rect.intersects(player.boundingBox, cactus.boundingBox)) {
            player.die()
            cactus.stop()

            clouds?.stop()
        }
    }

    private fun draw(){
        surfaceHolder?.let { it ->
            if (it.surface.isValid){
                canvas = it.lockCanvas()
                canvas?.drawColor(resources.getColor(R.color.game_bg_color))

                paint.color = resources.getColor(R.color.game_paint_color)
                paint.strokeWidth = 5f
                canvas?.drawLine(0f, height - 500f, width.toFloat(), height - 500f, paint)

                player.draw(canvas, paint)
                cactus.draw(canvas, paint)

                sun?.draw(canvas, paint)
                clouds?.draw(canvas, paint)

                surfaceHolder?.unlockCanvasAndPost(canvas)
            }
        }
    }

    private fun control(){
        Thread.sleep(17L)
    }

    fun pause(){
        playing = false
        gameThread?.join()
    }

    fun resume(){
        playing = true
        gameThread = Thread(this)
        gameThread!!.start()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action){
            MotionEvent.ACTION_DOWN -> {
                if (player.alive)
                    player.jump()
                else
                    init(context, screenWidth, screenHeight, weather)
            }
        }
        return true
    }
}