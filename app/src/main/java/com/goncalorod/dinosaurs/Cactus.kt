package com.goncalorod.dinosaurs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import java.util.*

class Cactus {

    private val random: Random

    private var maxX: Int
    private var maxY: Int

    private var sprite: Bitmap

    private var offsetX: Float
    private var offsetY: Float

    private var posX = 0

    constructor(context: Context?, width: Int, height: Int) {
        sprite = BitmapFactory.decodeResource(context?.resources, R.drawable.cactus)
        sprite = Bitmap.createScaledBitmap(sprite, sprite.width / spriteScale, sprite.height / spriteScale, false)

        offsetX = sprite.width / 2f
        offsetY = sprite.height.toFloat()

        maxX = width
        maxY = height

        random = Random()
        generatePosition()
    }

    fun update() {
        posX -= speed

        if (posX < -offsetX)
            generatePosition()
    }

    fun draw(canvas: Canvas?, paint: Paint) {
        canvas?.drawBitmap(sprite, posX - offsetX, maxY - offsetY - 450f, paint)
    }

    private fun generatePosition() {
        posX = maxX + 300 + (-250 + random.nextInt(500))
    }

    companion object {
        private const val spriteScale = 15

        private const val speed = 25
    }
}