package com.murkgom.triple_arrangement.dialog

import android.content.Context
import android.content.SharedPreferences
import com.murkgom.triple_arrangement.R
import com.murkgom.triple_arrangement.record.RecordViewGroup

class GameOverDialog(private val context: Context): CustomDialog(context = context, layoutResourceId = R.layout.dialog_game_over) {
    private lateinit var recordViewGroups: Map<String, RecordViewGroup>
    private lateinit var sharedPref: SharedPreferences
    private lateinit var maxScoreKey: String
    private lateinit var maxComboKey: String
    private lateinit var maxLevelKey: String
    private val nowScoreKey: String = "nowScoreKey"
    private val nowComboKey: String = "nowComboKey"

    init {
        initPref()
        initView()
    }

    private fun initPref() {
        sharedPref = context.getSharedPreferences(context.getString(R.string.record_preference_key), Context.MODE_PRIVATE)
        maxScoreKey = context.getString(R.string.key_max_score)
        maxComboKey = context.getString(R.string.key_max_combo)
        maxLevelKey = context.getString(R.string.key_max_level)

    }

    private fun initView() {
        recordViewGroups = mapOf(
                Pair(maxScoreKey, RecordViewGroup(dialog.findViewById(R.id.max_score))),
                Pair(nowScoreKey, RecordViewGroup(dialog.findViewById(R.id.now_score))),
                Pair(maxComboKey, RecordViewGroup(dialog.findViewById(R.id.max_combo))),
                Pair(nowComboKey, RecordViewGroup(dialog.findViewById(R.id.now_combo)))
        )
    }

    fun show(score: Int, combo: Int, level: Int) {
        setRecordView(score, nowScoreKey)
        setRecordView(combo, nowComboKey)
        setRecord(score, maxScoreKey)
        setRecord(combo, maxComboKey)
        setRecord(level, maxLevelKey)

        dialog.window
                ?.attributes
                ?.y = -200
        dialog.show()
    }

    private fun setRecordView(record: Int, recordKey: String) {
        recordViewGroups[recordKey]?.setRecord(record)
    }

    private fun setRecord(nowRecord: Int, recordKey: String) {
        var maxRecord = sharedPref.getInt(recordKey, 0)
        val viewGroup: RecordViewGroup? = recordViewGroups[recordKey]

        if (maxRecord < nowRecord) {
            maxRecord = nowRecord
            viewGroup?.showNewSign()
        } else {
            viewGroup?.hideNewSign()
        }

        viewGroup?.setRecord(maxRecord)

        with(sharedPref.edit()) {
            putInt(recordKey, maxRecord)
            commit()
        }
    }
}