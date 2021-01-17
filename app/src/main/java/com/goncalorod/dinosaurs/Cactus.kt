package com.goncalorod.dinosaurs

import android.content.Context
import android.graphics.*
import java.util.*

class Cactus {

    private var running = true

    private val random: Random

    private var maxX: Int
    private var maxY: Int

    private var sprite: Bitmap

    private var offsetX: Float
    private var offsetY: Float

    private var posX = 0

    var boundingBox: Rect
        private set

    constructor(context: Context?, width: Int, height: Int) {
        sprite = BitmapFactory.decodeResource(context?.resources, R.drawable.cactus)
        sprite = Bitmap.createScaledBitmap(sprite, sprite.width / spriteScale, sprite.height / spriteScale, false)

        offsetX = sprite.width / 2f
        offsetY = sprite.height.toFloat()

        maxX = width
        maxY = height

        random = Random()
        generatePosition()

        boundingBox = Rect((posX - offsetX).toInt(), (maxY - offsetY - 450).toInt(), sprite.width, sprite.height)
    }

    fun update() {
        if (!running)
            return

        posX -= speed

        if (posX < -offsetX)
            generatePosition()

        boundingBox.left    = (posX - offsetX).toInt()
        boundingBox.top     = ((maxY - offsetY - 450).toInt())
        boundingBox.right   = boundingBox.left + sprite.width
        boundingBox.bottom  = boundingBox.top + sprite.height
    }

    fun draw(canvas: Canvas?, paint: Paint) {
        canvas?.drawBitmap(sprite, posX - offsetX, maxY - offsetY - 450f, paint)
    }

    private fun generatePosition() {
        posX = maxX + 300 + (-250 + random.nextInt(500))
    }

    fun stop() {
        running = false
    }

    companion object {
        private const val spriteScale = 15

        private const val speed = 25
    }
}