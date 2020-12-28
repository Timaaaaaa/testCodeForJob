package com.thousand.aidynnury.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class InfoNet (
    @SerializedName("id")var id: Int? = null,
    @SerializedName("whatsapp")var whatsapp: String? = null,
    @SerializedName("kaspi_description")var kaspi_description: String? = null,
    @SerializedName("kaspi")var kaspi: String? = null,
    @SerializedName("phone")var phone: String? = null,
    @SerializedName("oferta")var privacy_policy: String? = null,
    @SerializedName("email")var email: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
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
        parcel.writeString(whatsapp)
        parcel.writeString(kaspi_description)
        parcel.writeString(kaspi)
        parcel.writeString(phone)
        parcel.writeString(email)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InfoNet> {
        override fun createFromParcel(parcel: Parcel): InfoNet {
            return InfoNet(parcel)
        }

        override fun newArray(size: Int): Array<InfoNet?> {
            return arrayOfNulls(size)
        }
    }
}
