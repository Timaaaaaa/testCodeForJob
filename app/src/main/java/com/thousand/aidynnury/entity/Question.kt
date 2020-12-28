package com.thousand.aidynnury.entity

import com.google.gson.annotations.SerializedName

class Question (
    @SerializedName("answer")var answer: String? = null,
    @SerializedName("question")var ask: String? = null,
    @SerializedName("id")var id: Int? = null,
    var isSelected : Boolean =false
)
