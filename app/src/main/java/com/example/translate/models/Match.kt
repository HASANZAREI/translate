package com.example.translate.models

import com.google.gson.annotations.SerializedName

data class Match(
    @SerializedName("create-date")
    val createDate: String,
    @SerializedName("created-by")
    val createdBy: String,
    @SerializedName("id")
    val id: Long,
    @SerializedName("last-update-date")
    val lastUpdateDate: String,
    @SerializedName("last-updated-by")
    val lastUpdatedBy: String,
    @SerializedName("match")
    val match: Double,
    @SerializedName("quality")
    val quality: Int,
    @SerializedName("reference")
    val reference: String?,
    @SerializedName("segment")
    val segment: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("subject")
    val subject: String,
    @SerializedName("target")
    val target: String,
    @SerializedName("translation")
    val translation: String,
    @SerializedName("usage-count")
    val usageCount: Int
)