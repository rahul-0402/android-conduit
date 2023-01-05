package com.rahulghag.conduit.data.remote.dtos.response


import com.google.gson.annotations.SerializedName

data class TagsResponse(
    @SerializedName("tags")
    val tags: List<String>
)