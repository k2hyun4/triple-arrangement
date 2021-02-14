package com.murkgom.triple_arrangement

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import com.murkgom.triple_arrangement.enums.BlockType
import com.murkgom.triple_arrangement.option.StartOption

class MainActivity: AppCompatActivity() {
    private lateinit var sharedPref: SharedPreferences
    private lateinit var myOptionStartLevelKey: String
    private lateinit var myOptionComboModeFlagKey: String
    private lateinit var startLevelPicker: NumberPicker
    private lateinit var comboModeSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSharedPrefs()
        initStartLevelPicker()
        initComboModeSwitch()
        val comboModeSwitch: Switch = findViewById(R.id.switch_combo_mode)
        findViewById<Button>(R.id.game_start)
                .setOnClickListener {
                    val intent = Intent(this, PlayActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

                    val selectedLevel = startLevelPicker.value
                    val selectedComboModeFlag = comboModeSwitch.isChecked

                    with(sharedPref.edit()) {
                        putBoolean(myOptionComboModeFlagKey, selectedComboModeFlag)
                        putInt(myOptionStartLevelKey, selectedLevel)
                        commit()
                    }

                    //Parcel에서 boolean을 권장하지 않음
                    val comboModeFlagByte = if (selectedComboModeFlag) {1} else {0}
                    val startOption = StartOption(selectedLevel, comboModeFlagByte)
                    intent.putExtra(this.getString(R.string.extra_start_option_key), startOption)
                    startActivity(intent)
                }
    }

    private fun initSharedPrefs() {
        sharedPref = this.getSharedPreferences(this.getString(R.string.record_preference_key), Context.MODE_PRIVATE)
        myOptionStartLevelKey = this.getString(R.string.key_my_option_start_level)
        myOptionComboModeFlagKey = this.getString(R.string.key_my_option_combo_mode_flag)
    }

    private fun initStartLevelPicker() {
        startLevelPicker = findViewById(R.id.start_level)
        val minLevel = BlockType.values().first().level
        val myMaxLevel = sharedPref.getInt(this.getString(R.string.key_max_level), minLevel)
        val myOptionLevel = sharedPref.getInt(myOptionStartLevelKey, minLevel)
        startLevelPicker.minValue = minLevel
        startLevelPicker.maxValue = myMaxLevel
        startLevelPicker.displayedValues = Array(myMaxLevel - minLevel + 1) { i ->
            String.format(this.getString(R.string.start_level_picker_label, i + 1, i + 2))
        }
        startLevelPicker.value = myOptionLevel
    }

    private fun initComboModeSwitch() {
        comboModeSwitch = findViewById(R.id.switch_combo_mode)
        val myOptionComboMode = sharedPref.getBoolean(myOptionComboModeFlagKey, false)
        comboModeSwitch.isChecked = myOptionComboMode
    }
}