package com.thousand.aidynnury.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class LessonItem(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("course_id") var course_id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("video_fon") var video_fon: String? = null,
    @SerializedName("homework") var homework: String? = null,
    @SerializedName("video_url") var video_url: String? = null,
    @SerializedName("description") var description: String? = null,

    @SerializedName("audios") var audios: MutableList<String> = mutableListOf(),
    @SerializedName("homework_audios") var homework_audios: MutableList<String> = mutableListOf()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeValue(course_id)
        parcel.writeString(title)
        parcel.writeString(video_fon)
        parcel.writeString(homework)
        parcel.writeString(video_url)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LessonItem> {
        override fun createFromParcel(parcel: Parcel): LessonItem {
            return LessonItem(parcel)
        }

        override fun newArray(size: Int): Array<LessonItem?> {
            return arrayOfNulls(size)
        }
    }
}