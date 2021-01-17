package com.goncalorod.dinosaurs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint

class Player {

    private var maxX = 0
    private var maxY = 0

    private var runSprite1 : Bitmap
    private var runSprite2 : Bitmap
    private var deadSprite : Bitmap

    private var offsetX : Float
    private var offsetY : Float

    private var frames = 0
    private var animationFrame = 1

    private var speedY = 0
    private var posY = 0

    private var grounded = true

    constructor(context: Context?, width: Int, height: Int) {
        runSprite1 = BitmapFactory.decodeResource(context?.resources, R.drawable.dinorun1)
        runSprite2 = BitmapFactory.decodeResource(context?.resources, R.drawable.dinorun2)
        deadSprite = BitmapFactory.decodeResource(context?.resources, R.drawable.dinodead)

        runSprite1 = Bitmap.createScaledBitmap(runSprite1, runSprite1.width / spriteScale, runSprite1.height / spriteScale, false)
        runSprite2 = Bitmap.createScaledBitmap(runSprite2, runSprite2.width / spriteScale, runSprite2.height / spriteScale, false)
        deadSprite = Bitmap.createScaledBitmap(deadSprite, deadSprite.width / spriteScale, deadSprite.height / spriteScale, false)

        offsetX = runSprite1.width / 2f
        offsetY = runSprite1.height.toFloat()

        maxX = width
        maxY = height
    }

    fun update() {
        // Update position
        posY += speedY

        if (posY < 0)
            posY = 0

        grounded = posY == 0

        if (!grounded)
            speedY -= 2

        // Animation
        frames++

        if (frames % animationFrames == 0) {
            animationFrame++

            if (animationFrame > 2)
                animationFrame = 1
        }
    }

    fun draw(canvas: Canvas?, paint: Paint) {
        val spriteToDraw = if (animationFrame == 1)
            runSprite1
        else
            runSprite2

        canvas?.drawBitmap(spriteToDraw, 150f - offsetX, maxY - offsetY - posY- 450f, paint)
    }

    fun jump() {
        if (!grounded)
            return

        speedY = jumpSpeed
    }

    companion object {
        private const val spriteScale = 15

        private const val animationFrames = 4

        private const val jumpSpeed = 35
    }
}