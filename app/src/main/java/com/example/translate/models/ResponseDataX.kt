package com.example.translate.models

import com.google.gson.annotations.SerializedName

data class ResponseDataX(
    @SerializedName("match")
    val match: Double,
    @SerializedName("translatedText")
    val translatedText: String
)