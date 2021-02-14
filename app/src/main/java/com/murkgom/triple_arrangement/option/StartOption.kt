package com.murkgom.triple_arrangement.option

import android.os.Parcel
import android.os.Parcelable

class StartOption(private var level: Int, private var comboModeFlag: Int): Parcelable {
    constructor(parcel: Parcel): this(
            parcel.readInt(),
            parcel.readInt()
    )

    fun getLevel(): Int {
        return this.level
    }

    fun getComboModeFlag(): Boolean {
        return this.comboModeFlag == 1
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(level)
        parcel.writeInt(comboModeFlag)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR: Parcelable.Creator<StartOption> {
        override fun createFromParcel(parcel: Parcel): StartOption {
            return StartOption(parcel)
        }

        override fun newArray(size: Int): Array<StartOption?> {
            return arrayOfNulls(size)
        }
    }

}