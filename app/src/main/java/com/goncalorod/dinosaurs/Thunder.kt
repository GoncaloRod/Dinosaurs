package com.goncalorod.dinosaurs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import java.util.*

class Thunder {

    private var maxX: Int
    private var maxY: Int

    private var sprite: Bitmap

    private var random: Random

    private var offsetX: Int

    private var posX = 0
    private var timer = 0
    private var showTimer = showTime

    constructor(context: Context?, width: Int, height: Int) {
        maxX = width
        maxY = height

        sprite = BitmapFactory.decodeResource(context?.resources, R.drawable.thunder)
        sprite = Bitmap.createScaledBitmap(sprite, sprite.width / scale, sprite.height / scale, false)

        random = Random()
        timer = random.nextInt(maxTimer)

        offsetX = sprite.width / 2
        posX = offsetX + random.nextInt(maxX - sprite.width)
    }

    fun update() {
        if (timer > 0)
        {
            timer--
        }
        else
        {
            if (showTimer > 0)
            {
                showTimer--
            }
            else
            {
                timer = random.nextInt(maxTimer)
                showTimer = showTime
                posX = offsetX + random.nextInt(maxX - sprite.width)
            }
        }
    }

    fun draw(canvas: Canvas?, paint: Paint) {
        if (timer > 0)
            return

        canvas?.drawBitmap(sprite, (posX - offsetX).toFloat(), 250.toFloat(), paint)
    }

    companion object {
        private const val scale = 10

        private const val maxTimer = 120
        private const val showTime = 60
    }
}