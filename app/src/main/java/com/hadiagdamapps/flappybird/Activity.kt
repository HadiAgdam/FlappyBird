package com.hadiagdamapps.flappybird

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

abstract class Activity(private val layout: Int) : AppCompatActivity(layout) {

    abstract fun main()

    abstract fun initViews()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
        initViews()
        main()
    }
}