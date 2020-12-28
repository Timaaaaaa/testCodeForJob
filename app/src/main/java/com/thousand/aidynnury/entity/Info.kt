package com.thousand.aidynnury.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


@Suppress("UNREACHABLE_CODE")
class Info(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("show") var show: Int? = null,
    @SerializedName("cat_id") var cat_id: Int? = null,
    @SerializedName("title") var title: String?= null,
    @SerializedName("description") var description: String?= null,
    @SerializedName("created_at") var created_at: String?= null,
    var isSelected: Boolean = false,
    @SerializedName("images") var images: MutableList<String>?= null,
    @SerializedName("videos") var videos: MutableList<String>?= null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(show)
        parcel.writeValue(cat_id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(created_at)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Info> {
        override fun createFromParcel(parcel: Parcel): Info {
            return Info(parcel)
        }

        override fun newArray(size: Int): Array<Info?> {
            return arrayOfNulls(size)
        }
    }
}