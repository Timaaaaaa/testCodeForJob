package com.thousand.aidynnury.entity

import com.google.gson.annotations.SerializedName

class Version (
    @SerializedName("ios")var ios: Int? = null,
    @SerializedName("android")var android: Int? = null
)
