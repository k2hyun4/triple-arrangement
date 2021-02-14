package com.murkgom.triple_arrangement

import android.os.Bundle
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import com.murkgom.triple_arrangement.dialog.GameOverDialog
import com.murkgom.triple_arrangement.dialog.MenuDialog
import com.murkgom.triple_arrangement.line.LineController
import com.murkgom.triple_arrangement.option.StartOption
import com.murkgom.triple_arrangement.record.Combo
import com.murkgom.triple_arrangement.record.Score
import com.murkgom.triple_arrangement.timer.AddNewRowTimer

class PlayActivity : AppCompatActivity() {
    lateinit var score: Score
    lateinit var combo: Combo
    private lateinit var gameOverDialog: GameOverDialog
    private lateinit var menuDialog: MenuDialog
    lateinit var lineController: LineController
    lateinit var addNewRowTimer: AddNewRowTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        score = Score(findViewById(R.id.score), this)
        val startOption = intent.getParcelableExtra<StartOption>(this.getString(R.string.extra_start_option_key))

        if (startOption != null) {
            score.setting(startOption.getLevel())
        }
        combo = Combo(this, findViewById(R.id.combo))

        val rootLayout = findViewById<LinearLayout>(R.id.root)
        val context = this
        rootLayout.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    initAddNewRowTimer()
                    initDialogs()
                    lineController = LineController(context,
                        arrayOf(
                            findViewById(R.id.wrapper_line_left),
                            findViewById(R.id.wrapper_line_middle),
                            findViewById(R.id.wrapper_line_right)
                        )
                    )

                    setOnClickTimeBar()
                    rootLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            }
        )
    }

    private fun initDialogs() {
        menuDialog = MenuDialog(this)
        findViewById<Button>(R.id.menu)
                .setOnClickListener {menuDialog.show()}

        gameOverDialog = GameOverDialog(this)
    }

    private fun setOnClickTimeBar() {
        findViewById<RelativeLayout>(R.id.time_bar)
                .setOnClickListener {
                    if (lineController.checkLinesAddableBlock()) {
                        lineController.addNewRow()
                        score.plusForTimeBarClick()
                        combo.resetMoveCount()
                    }
                }
    }

    private fun initAddNewRowTimer() {
        addNewRowTimer = AddNewRowTimer(this)
    }

    fun addNewRow() {
        if (!lineController.checkLinesAddableBlock()) {
            gameOver()
        }

        lineController.addNewRow()
    }

    private fun gameOver() {
        addNewRowTimer.stop()
        gameOverDialog.show(score.getScore(),
                combo.getMaxCombo(),
                score.getLevel())
    }

    fun resetAll() {
        score.resetAll()
        combo.resetAll()
        lineController.resetAll()
        addNewRowTimer.start()
    }

    override fun onBackPressed() {
        menuDialog.show()
    }
}