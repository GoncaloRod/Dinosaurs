package com.goncalorod.dinosaurs

import android.content.Context
import android.graphics.*
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Player {

    private var user: FirebaseUser

    private var maxX: Int
    private var maxY: Int

    private var context: Context?

    private var runSprite1: Bitmap
    private var runSprite2: Bitmap
    private var deadSprite: Bitmap

    private var offsetX: Float
    private var offsetY: Float

    private var frames = 0
    private var animationFrame = 1

    private var speedY = 0
    private var posY = 0

    private var grounded = true

    private var score: Float = 0f

    var alive = true
        private set

    var boundingBox: Rect
        private set

    constructor(context: Context?, width: Int, height: Int) {
        user = Firebase.auth.currentUser!!

        this.context = context

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

        boundingBox = Rect((150 - offsetX).toInt(), (maxY - offsetY - posY - 450).toInt(), runSprite1.width, runSprite2.height)
    }

    fun update() {
        if (!alive)
            return

        // Increment score
        score += 0.25f

        // Update position
        posY += speedY

        if (posY < 0)
            posY = 0

        grounded = posY == 0

        if (!grounded)
            speedY -= 2

        // Update bounding box
        boundingBox.left    = (150 - offsetX).toInt() + 30
        boundingBox.top     = ((maxY - offsetY - posY - 450).toInt())
        boundingBox.right   = boundingBox.left + runSprite1.width - 80
        boundingBox.bottom  = boundingBox.top + runSprite1.height

        // Animation
        frames++

        if (frames % animationFrames == 0) {
            animationFrame++

            if (animationFrame > 2)
                animationFrame = 1
        }
    }

    fun draw(canvas: Canvas?, paint: Paint) {
        val spriteToDraw = if (alive) {
            if (animationFrame == 1) {
                runSprite1
            }
            else {
                runSprite2
            }
        }
        else {
            deadSprite
        }

        /* Collider debug
        paint.color = Color.MAGENTA
        canvas?.drawRect(boundingBox, paint)
        */

        canvas?.drawBitmap(spriteToDraw, 150f - offsetX, maxY - offsetY - posY - 450f, paint)

        paint.color = context?.resources?.getColor(R.color.game_paint_color) ?: Color.RED
        paint.textSize = textSize
        paint.typeface = context?.resources?.getFont(R.font.disposable_droid_bb)
        canvas?.drawText("${context?.resources?.getString(R.string.score_label)}: ${score.toInt()}", scoreOffsetX, textSize + scoreOffsetY, paint)
    }

    fun jump() {
        if (!grounded or !alive)
            return

        speedY = jumpSpeed
    }

    fun die() {
        if (!alive)
            return

        alive = false

        storeScore()
    }

    private fun storeScore() {
        val db = Firebase.firestore

        db.collection("scores").document(user.uid).get().addOnSuccessListener {
            if (it.getLong("score") == null) {
                val score = hashMapOf(
                        "player_name" to user.displayName,
                        "score" to score.toInt()
                )

                db.collection("scores").document(user.uid).set(score)
            } else {
                if (it.getLong("score")!!.toInt() <= score) {
                    val score = hashMapOf(
                            "player_name" to user.displayName,
                            "score" to score.toInt()
                    )

                    db.collection("scores").document(user.uid).set(score)
                }
            }
        }
    }

    companion object {
        private const val spriteScale = 15

        private const val animationFrames = 4

        private const val jumpSpeed = 30

        private const val textSize = 60f
        private const val scoreOffsetX = 15f
        private const val scoreOffsetY = 15f
    }
}