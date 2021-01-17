package com.goncalorod.dinosaurs

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint

class Sun {

    private var maxX: Int
    private var maxY: Int

    private var sprite: Bitmap

    private var offsetX: Int

    constructor(context: Context?, width: Int, height: Int) {
        maxX = width
        maxY = height

        sprite = BitmapFactory.decodeResource(context?.resources, R.drawable.sun)
        sprite = Bitmap.createScaledBitmap(sprite, sprite.width / scale, sprite.height / scale, false)

        offsetX = sprite.width
    }

    fun draw(canvas: Canvas?, paint: Paint) {
        canvas?.drawBitmap(sprite, (maxX - offsetX).toFloat(), 0f, paint)
    }

    companion object {
        private const val scale = 10
    }
}