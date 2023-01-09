package com.rahulghag.conduit.data.remote

import com.rahulghag.conduit.data.remote.dtos.request.SignInRequest
import com.rahulghag.conduit.data.remote.dtos.request.SignUpRequest
import com.rahulghag.conduit.data.remote.dtos.response.ArticlesResponse
import com.rahulghag.conduit.data.remote.dtos.response.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ConduitApi {
    @POST("api/users/login")
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): Response<UserResponse>

    @POST("api/users")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<UserResponse>

    @GET("api/articles")
    suspend fun getArticleList(): Response<ArticlesResponse>

    companion object {
        const val BASE_URL = "https://api.realworld.io/"
    }
}