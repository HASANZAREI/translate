package com.example.translate.models

import com.google.gson.annotations.SerializedName

data class ResponseData(
    @SerializedName("exception_code")
    val exceptionCode: Int,
    @SerializedName("matches")
    val matches: List<Match>,
    @SerializedName("mtLangSupported")
    val mtLangSupported: Any,
    @SerializedName("quotaFinished")
    val quotaFinished: Boolean,
    @SerializedName("responderId")
    val responderId: Int?,
    @SerializedName("responseData")
    val responseData: ResponseDataX,
    @SerializedName("responseDetails")
    val responseDetails: String,
    @SerializedName("responseStatus")
    val responseStatus: Int
)