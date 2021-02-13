package com.murkgom.triple_arrangement

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import com.murkgom.triple_arrangement.enums.BlockType
import com.murkgom.triple_arrangement.option.StartOption

class MainActivity: AppCompatActivity() {
    private lateinit var startLevelPicker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initStartLevelPicker()
        findViewById<Button>(R.id.game_start)
                .setOnClickListener {
                    val intent = Intent(this, PlayActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    val startOption = StartOption(startLevelPicker.value)
                    intent.putExtra(this.getString(R.string.extra_start_option_key), startOption)
                    startActivity(intent)
                }
    }

    private fun initStartLevelPicker() {
        startLevelPicker = findViewById(R.id.start_level)
        val minLevel = BlockType.values().first().level
        val myMaxLevel = this.getSharedPreferences(this.getString(R.string.record_preference_key), Context.MODE_PRIVATE)
            .getInt(this.getString(R.string.max_level_key), 1)
        startLevelPicker.minValue = minLevel
        startLevelPicker.maxValue = myMaxLevel
        startLevelPicker.displayedValues = Array(myMaxLevel - minLevel + 1) { i ->
            String.format("레벨%d (블럭 종류 : %d개)", i + 1, i + 2)
        }
    }
}