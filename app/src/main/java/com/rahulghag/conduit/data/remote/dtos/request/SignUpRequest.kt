package com.rahulghag.conduit.data.remote.dtos.request


import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("user")
    val signUp: SignUpDto
)