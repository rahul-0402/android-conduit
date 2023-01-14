package com.rahulghag.conduit.features.articles.data.remote.dtos.request


import com.google.gson.annotations.SerializedName

data class CreateArticleRequest(
    @SerializedName("article")
    val article: CreateArticleDto
)