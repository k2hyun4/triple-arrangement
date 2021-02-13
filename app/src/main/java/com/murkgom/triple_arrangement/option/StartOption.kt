package com.murkgom.triple_arrangement.option

import android.os.Parcel
import android.os.Parcelable

class StartOption(private var level: Int): Parcelable {
    constructor(parcel: Parcel): this(
            parcel.readInt()
    )

    fun getLevel(): Int {
        return this.level
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(level)
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