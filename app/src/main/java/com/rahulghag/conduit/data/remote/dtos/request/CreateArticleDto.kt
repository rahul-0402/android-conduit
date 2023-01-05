package com.rahulghag.conduit.data.remote.dtos.request

import com.google.gson.annotations.SerializedName

data class CreateArticleDto(
    @SerializedName("body")
    val body: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("tagList")
    val tagList: List<String> = emptyList(),
    @SerializedName("title")
    val title: String
)