package com.rahulghag.conduit.features.auth.data.remote.dtos.request


import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("user")
    val signUp: SignUpDto
)