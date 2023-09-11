package com.hadiagdamapps.flappybird

import android.os.Handler
import android.view.View

abstract class Jumper(private val cursor: View) {

    private val strength = 400
    var enabled = true
    private val rotation = 30f
    private val handler = Handler()


    abstract fun reachedTop()


    private fun rotateUp() {
        cursor.rotation = -rotation
        handler.removeCallbacks(rotateDown)
        handler.postDelayed(rotateDown, 250)
    }

    private val rotateDown = Runnable {
        cursor.rotation = rotation
    }

    fun jump() {
        if (!enabled) return

        cursor.y -= strength
        rotateUp()


        if (cursor.y <= 0) reachedTop()
    }

}