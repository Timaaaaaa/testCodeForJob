package com.thousand.aidynnury.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class Lesson (
    @SerializedName("id") var id : Int? = null,
    @SerializedName("title")var title : String?,
    @SerializedName("image")var image : String?,
    @SerializedName("author") var author: String?,
    @SerializedName("description")var description : String?= null,
    @SerializedName("price")var price : Int? = null,
    @SerializedName("lesson_count")var index : Int,
    @SerializedName("bought") var isSelected : Boolean = false,
    @SerializedName("lessons") var lessons: MutableList<CourseLessons> = mutableListOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(image)
        parcel.writeString(author)
        parcel.writeString(description)
        parcel.writeValue(price)
        parcel.writeInt(index)
        parcel.writeByte(if (isSelected) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Lesson> {
        override fun createFromParcel(parcel: Parcel): Lesson {
            return Lesson(parcel)
        }

        override fun newArray(size: Int): Array<Lesson?> {
            return arrayOfNulls(size)
        }
    }
}