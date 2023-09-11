package com.hadiagdamapps.flappybird

import android.graphics.Point
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast

class MainActivity : Activity(R.layout.activity_main) {

    private lateinit var cursor: View
    private lateinit var gravity: Gravity
    private lateinit var jumper: Jumper
    private lateinit var pipeGenerator: PipeGenerator
    private lateinit var container: RelativeLayout

    private fun getScreen(): Point {
        var point = Point()
        windowManager.defaultDisplay.getSize(point)

        return point
    }

    private fun loose() {
        Toast.makeText(this@MainActivity, "lost", Toast.LENGTH_LONG).show()
        gravity.pause()
        jumper.enabled = false
        pipeGenerator.running = false
    }

    private fun setCursorPos() {
        cursor.y = (getScreen().y / 3).toFloat()
        cursor.x = (0 - getScreen().x / 2).toFloat()

        Log.e("y", (getScreen().y / 2).toFloat().toString())
    }

    override fun main() {
        setCursorPos()
        gravity = object : Gravity(cursor, getScreen().y) {
            override fun reachedGround() {
                loose()
            }
        }

        jumper = object : Jumper(cursor) {
            override fun reachedTop() {
                loose()
            }
        }

        pipeGenerator = object : PipeGenerator(this, container, getScreen(), cursor) {
            override fun touchedPipe() {
                loose()
            }
        }

        pipeGenerator.running = true
        gravity.resume()
    }

    override fun initViews() {
        cursor = findViewById(R.id.cursor)
        container = findViewById(R.id.container)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        if (event != null && event.action == MotionEvent.ACTION_DOWN) {
            jumper.jump()
        }

        return super.onTouchEvent(event)
    }
}