package com.goncalorod.dinosaurs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint

class Clouds {

    private var running = true

    private var maxX: Int
    private var maxY: Int

    private var cloud1Sprite: Bitmap
    private var cloud2Sprite: Bitmap

    private var offsetX: Int

    private var posX = 0

    constructor(context: Context?, width: Int, height: Int) {
        maxX = width
        maxY = height

        cloud1Sprite = BitmapFactory.decodeResource(context?.resources, R.drawable.cloud1)
        cloud2Sprite = BitmapFactory.decodeResource(context?.resources, R.drawable.cloud2)

        cloud1Sprite = Bitmap.createScaledBitmap(cloud1Sprite, cloud1Sprite.width / scale, cloud1Sprite.height / scale, false)
        cloud2Sprite = Bitmap.createScaledBitmap(cloud2Sprite, cloud2Sprite.width / scale, cloud2Sprite.height / scale, false)

        offsetX = cloud1Sprite.width / 2

        posX = offsetX
    }

    fun update() {
        if (!running)
            return

        posX -= speed
    }

    fun draw(canvas: Canvas?, paint: Paint) {
        var pos = posX

        while (pos < maxX + offsetX) {
            canvas?.drawBitmap(cloud2Sprite, pos.toFloat(), 125f, paint)
            canvas?.drawBitmap(cloud1Sprite, (pos - offsetX).toFloat(), 150f, paint)

            pos += cloud1Sprite.width + spacing
        }
    }

    fun stop() {
        running = false
    }

    companion object {
        private const val scale = 8

        private const val spacing = 25

        private const val speed = 1
    }
}