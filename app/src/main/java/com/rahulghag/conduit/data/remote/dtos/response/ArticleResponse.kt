package com.rahulghag.conduit.data.remote.dtos.response


import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("article")
    val article: ArticleDto
)