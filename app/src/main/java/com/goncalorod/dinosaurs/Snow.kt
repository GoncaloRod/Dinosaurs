package com.goncalorod.dinosaurs

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import java.util.*

class Snow {
    private var maxX: Int
    private var maxY: Int

    private var color: Int

    private val random: Random

    private var posX: Int = 0
    private var posY: Int = 0

    constructor(context: Context?, width: Int, height: Int) {
        maxX = width
        maxY = height

        color = context?.resources?.getColor(R.color.game_paint_color) ?: Color.RED

        random = Random()
        generatePosition()
        posY = startingY + random.nextInt(maxY - startingY)
    }

    fun update() {
        posY += speed

        if (posY > maxY)
            generatePosition()
    }

    fun draw(canvas: Canvas?, paint: Paint) {
        paint.color = color
        canvas?.drawCircle(posX.toFloat(), posY.toFloat(), radius, paint)
    }

    private fun generatePosition() {
        posY = startingY
        posX = random.nextInt(maxX)
    }

    companion object {
        private const val startingY = 300
        private const val speed = 50

        private const val radius = 5f
    }
}