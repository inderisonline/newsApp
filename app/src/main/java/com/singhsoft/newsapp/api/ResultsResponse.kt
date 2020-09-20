package com.singhsoft.newsapp.api

import com.google.gson.annotations.SerializedName

data class ResultsResponse<T>(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int?,
    @SerializedName("code")
    val code: String? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("articles")
    val articles: List<T>
)