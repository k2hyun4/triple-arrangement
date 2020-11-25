package com.example.triplearrangement

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.view.Window
import android.widget.Button

class GameOverDialog(private val context: Context) {
    private val dialog: Dialog = Dialog(context)
    private lateinit var recordViewGroups: Map<String, RecordViewGroup>
    private lateinit var sharedPref: SharedPreferences
    private lateinit var maxScoreKey: String
    private lateinit var maxComboKey: String
    private val nowScoreKey: String = "nowScoreKey"
    private val nowComboKey: String = "nowComboKey"

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_game_over)
        dialog.setCancelable(false)
        initAboutPref()
        initView()
    }

    fun getDialog(): Dialog {
        return dialog
    }

    fun run(score: Int, combo: Int) {
        setRecordView(score, nowScoreKey)
        setRecordView(combo, nowComboKey)
        setRecord(score, maxScoreKey)
        setRecord(combo, maxComboKey)

        dialog.show()
    }

    private fun initView() {
        recordViewGroups = mapOf(
                Pair(maxScoreKey, RecordViewGroup(dialog.findViewById(R.id.max_score))),
                Pair(nowScoreKey, RecordViewGroup(dialog.findViewById(R.id.now_score))),
                Pair(maxComboKey, RecordViewGroup(dialog.findViewById(R.id.max_combo))),
                Pair(nowComboKey, RecordViewGroup(dialog.findViewById(R.id.now_combo)))
        )

        dialog.findViewById<Button>(R.id.restart)
                .setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.goToMain)
                .setOnClickListener {
            // TODO: 2020-11-25 메뉴 액티비티 생성 후 처리
        }
    }

    private fun initAboutPref() {
        sharedPref = context.getSharedPreferences(context.getString(R.string.record_preference_key), Context.MODE_PRIVATE)
        maxScoreKey = context.getString(R.string.max_score_key)
        maxComboKey = context.getString(R.string.max_combo_key)
    }

    private fun setRecordView(record: Int, recordKey: String) {
        recordViewGroups[recordKey]?.setRecord(record)
    }

    private fun setRecord(nowRecord: Int, recordKey: String) {
        var maxRecord = sharedPref.getInt(recordKey, 0)
        val viewGroup: RecordViewGroup = recordViewGroups[recordKey] ?: error("")

        if (maxRecord < nowRecord) {
            maxRecord = nowRecord
            viewGroup.showNewSign()
        } else {
            viewGroup.hideNewSign()
        }

        viewGroup.setRecord(maxRecord)

        with(sharedPref.edit()) {
            putInt(recordKey, maxRecord)
            commit()
        }
    }
}