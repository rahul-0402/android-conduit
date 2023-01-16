package com.rahulghag.conduit.common.data.remote

import com.rahulghag.conduit.common.data.remote.dtos.response.ProfileResponse
import com.rahulghag.conduit.common.data.remote.dtos.response.UserResponse
import com.rahulghag.conduit.features.articles.data.remote.dtos.request.CreateArticleRequest
import com.rahulghag.conduit.features.articles.data.remote.dtos.response.ArticleResponse
import com.rahulghag.conduit.features.articles.data.remote.dtos.response.ArticlesResponse
import com.rahulghag.conduit.features.auth.data.remote.dtos.request.SignInRequest
import com.rahulghag.conduit.features.auth.data.remote.dtos.request.SignUpRequest
import retrofit2.Response
import retrofit2.http.*

interface ConduitApi {
    @POST("users/login")
    suspend fun signIn(
        @Body signInRequest: SignInRequest
    ): Response<UserResponse>

    @POST("users")
    suspend fun signUp(
        @Body signUpRequest: SignUpRequest
    ): Response<UserResponse>

    @GET("articles?limit=100")
    suspend fun getArticles(): Response<ArticlesResponse>

    @GET("articles/{slug}")
    suspend fun getArticle(
        @Path("slug") slug: String
    ): Response<ArticleResponse>

    @POST("articles")
    suspend fun createArticle(
        @Body createArticleRequest: CreateArticleRequest
    ): Response<ArticleResponse>

    @GET("profiles/{username}")
    suspend fun getUserProfile(
        @Path("username") username: String
    ): Response<ProfileResponse>

    @POST("profiles/{username}/follow")
    suspend fun followUser(
        @Path("username") username: String
    ): Response<ProfileResponse>

    @DELETE("profiles/{username}/follow")
    suspend fun unfollowUser(
        @Path("username") username: String
    ): Response<ProfileResponse>

    @POST("articles/{slug}/favorite")
    suspend fun addArticleToFavorites(
        @Path("slug") slug: String
    ): Response<ArticleResponse>

    @DELETE("articles/{slug}/favorite")
    suspend fun removeArticleFromFavorites(
        @Path("slug") slug: String
    ): Response<ArticleResponse>

    companion object {
        const val BASE_URL = "https://api.realworld.io/api/"
    }
}