package com.thousand.aidynnury.entity

import com.google.gson.annotations.SerializedName

class FeedBack (
    @SerializedName("phone")var phone: String? = null,
    @SerializedName("email")var email: String? = null
)
