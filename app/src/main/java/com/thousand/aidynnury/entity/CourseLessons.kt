package com.thousand.aidynnury.entity

import com.google.gson.annotations.SerializedName

class CourseLessons (
    @SerializedName("video_fon")var video_fon: String? = null,
    @SerializedName("title")var title: String? = null,
    var allowed: Boolean = true,
    @SerializedName("id")var id: Int? = null
    )