package com.example.triplearrangement.timer

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.triplearrangement.PlayActivity
import java.util.*
import kotlin.concurrent.timer

class AddNewRowTimer(context: Context) {
    private lateinit var timer: Timer
    private val periodMillis: Long = 2_500
    private val playActivity: PlayActivity = context as PlayActivity

    init {
        start()
    }

    fun start() {
        Handler(Looper.getMainLooper()).postDelayed({
            timer = timer(period = periodMillis) {
                playActivity.runOnUiThread {
                    playActivity.addNewRow()
                }
            }
        }, periodMillis)
    }

    fun stop() {
        timer.cancel()
    }
}