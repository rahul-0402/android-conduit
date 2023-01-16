package com.rahulghag.conduit.features.articles.data.remote.dtos.request

import com.google.gson.annotations.SerializedName

data class CreateArticleDto(
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("body")
    val body: String
)