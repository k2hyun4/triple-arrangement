package com.murkgom.triple_arrangement.timer

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.murkgom.triple_arrangement.PlayActivity
import java.util.*
import kotlin.concurrent.timer

class AddNewRowTimer(context: Context) {
    private lateinit var timer: Timer
    private val periodMillis: Long = 2_500
    private val playActivity: PlayActivity = context as PlayActivity
    private var ready: Boolean = false

    init {
        start()
    }

    fun ready(): Boolean {
        return this.ready
    }

    fun start() {
        Handler(Looper.getMainLooper()).postDelayed({
            timer = timer(period = periodMillis) {
                ready = true
                playActivity.runOnUiThread {
                    playActivity.addNewRow()
                }
            }

        }, periodMillis)
    }

    fun stop() {
        timer.cancel()
        ready = false
    }
}