package com.rahulghag.conduit.features.articles.data.remote.dtos.response


import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("article")
    val article: ArticleDto
)