package com.rahulghag.conduit.data.remote.dtos.response


import com.google.gson.annotations.SerializedName

data class ArticlesResponse(
    @SerializedName("articles")
    val articles: List<ArticleDto>,
    @SerializedName("articlesCount")
    val articlesCount: Int
)