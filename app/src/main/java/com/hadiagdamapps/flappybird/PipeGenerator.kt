package com.hadiagdamapps.flappybird

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.Rect
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlin.random.Random

abstract class PipeGenerator(
    private val context: Context,
    private val container: RelativeLayout,
    private val screen: Point,
    private val cursor: View
) {

    init {
        generatePipes()
        movePips()
    }

    private val spaceSize = 800
    private val pipeWidth = 260
    private val pipeHeight = 1600
    private val pipeParams = RelativeLayout.LayoutParams(pipeWidth, pipeHeight)
    private val pipeImage = R.drawable.pipe
    private val speed = 15f
    private val pipes = ArrayList<View>()
    var running = false

    abstract fun touchedPipe()

    private fun addPipe(start: Int) {
        val bottomPipe = ImageView(context)
        bottomPipe.setImageResource(pipeImage)
        bottomPipe.scaleType = ImageView.ScaleType.FIT_XY

        val topPipe = ImageView(context)
        topPipe.setImageResource(pipeImage)
        topPipe.rotation = 180f
        topPipe.scaleType = ImageView.ScaleType.FIT_XY

        container.addView(bottomPipe, pipeParams)
        container.addView(topPipe, pipeParams)

        bottomPipe.y = start + spaceSize.toFloat()
        topPipe.y = start - pipeHeight.toFloat()

        bottomPipe.x = screen.x.toFloat()
        topPipe.x = screen.x.toFloat()

        pipes.add(topPipe)
        pipes.add(bottomPipe)
    }

    private fun movePips() {
        if (running)
            for (pipe in pipes) {
                pipe.x -= speed

//                val pipeRect =
//                    Rect(pipe.x.toInt(), pipe.top, (pipe.x + pipeWidth).toInt(), pipe.bottom)
                val pipeRect = Rect()
                pipe.getHitRect(pipeRect)

                val cursorRect = Rect()
                cursor.getHitRect(cursorRect)
                if (cursorRect.intersect(pipeRect)) {
                    touchedPipe()
                }

                if (pipe.x + pipeHeight < 0) {
                    container.removeView(pipe)
                }
            }
        Handler().postDelayed({ movePips() }, 10)
    }

    private fun generatePipes() {
        if (running)
            addPipe(Random.nextInt(0, screen.y - spaceSize))

        Handler().postDelayed(
            {
                generatePipes()
            }, 1500
        )
    }

}