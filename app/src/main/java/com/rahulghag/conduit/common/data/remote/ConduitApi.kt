package com.rahulghag.conduit.common.data.remote

import com.rahulghag.conduit.common.data.remote.dtos.response.UserResponse
import com.rahulghag.conduit.features.articles.data.remote.dtos.response.ArticleResponse
import com.rahulghag.conduit.features.articles.data.remote.dtos.response.ArticlesResponse
import com.rahulghag.conduit.features.auth.data.remote.dtos.request.SignInRequest
import com.rahulghag.conduit.features.auth.data.remote.dtos.request.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
    suspend fun getArticles(): Response<ArticlesResponse>

    @GET("/api/articles/{slug}")
    suspend fun getArticle(
        @Path("slug") slug: String
    ): Response<ArticleResponse>

    companion object {
        const val BASE_URL = "https://api.realworld.io/"
    }
}