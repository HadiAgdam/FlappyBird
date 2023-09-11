package com.hadiagdamapps.flappybird

import android.os.Handler
import android.util.Log
import android.view.View

abstract class Gravity(private val cursor: View, private val ground: Int) {

    abstract fun reachedGround()
    private var running = false
    private val speed = 16

    init {
        loop()
    }


    fun resume() {
        running = true
    }

    fun pause() {
        running = false
    }

    private fun loop() {
        Handler().postDelayed(
            {
                if (running) {

                    cursor.y += speed

                    if (cursor.y >= ground - cursor.height * 1.5)
                        reachedGround()
//
//                    Log.e("cursor.y", cursor.y.toString())
//                    Log.e("ground", ground.toString())
//                    Log.e("height", cursor.height.toString())
//                    Log.e("", "")


                }
                loop()
            }, 16
        )
    }


}