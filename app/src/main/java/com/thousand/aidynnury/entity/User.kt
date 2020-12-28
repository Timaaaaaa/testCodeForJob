package com.thousand.aidynnury.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class User(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("token") var token: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(phone)
        parcel.writeString(name)
        parcel.writeString(token)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}