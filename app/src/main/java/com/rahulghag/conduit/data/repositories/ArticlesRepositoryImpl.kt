package com.rahulghag.conduit.data.repositories

import com.rahulghag.conduit.R
import com.rahulghag.conduit.data.remote.ConduitApi
import com.rahulghag.conduit.data.remote.dtos.request.CreateArticleDto
import com.rahulghag.conduit.data.remote.dtos.request.CreateArticleRequest
import com.rahulghag.conduit.data.remote.dtos.response.ArticlesResponse
import com.rahulghag.conduit.domain.models.Article
import com.rahulghag.conduit.domain.repositories.ArticlesRepository
import com.rahulghag.conduit.domain.repositories.PreferencesManager
import com.rahulghag.conduit.utils.ArticleSortType
import com.rahulghag.conduit.utils.ErrorUtils
import com.rahulghag.conduit.utils.Resource
import com.rahulghag.conduit.utils.UiMessage
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class ArticlesRepositoryImpl(
    private val conduitApi: ConduitApi,
    private val preferencesManager: PreferencesManager
) : ArticlesRepository {

    override suspend fun getArticles(articleSortType: ArticleSortType): Resource<List<Article>> {
        return try {
            val response: Response<ArticlesResponse> = when (articleSortType) {
                ArticleSortType.ALL -> {
                    conduitApi.getArticles()
                }
                ArticleSortType.BY_USERNAME -> {
                    conduitApi.getArticlesByUsername(
                        username = preferencesManager.getUsername().first()
                    )
                }
                ArticleSortType.FAVORITED_BY_USERNAME -> {
                    conduitApi.getFavoritedArticlesByUsername(
                        username = preferencesManager.getUsername().first()
                    )
                }
            }
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val articles = responseBody.articles.map { it.toArticle() }
                    Resource.Success(data = articles)
                } else {
                    Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                }
            } else {
                Resource.Error(message = ErrorUtils.parseErrorResponse(response.errorBody()))
            }
        } catch (e: IOException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_no_internet_connection))
        } catch (e: HttpException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
        }
    }

    override suspend fun getArticle(slug: String): Resource<Article> {
        return try {
            val response =
                conduitApi.getArticle(slug = slug)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val article = responseBody.article.toArticle()
                    Resource.Success(data = article)
                } else {
                    Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                }
            } else {
                Resource.Error(message = ErrorUtils.parseErrorResponse(response.errorBody()))
            }
        } catch (e: IOException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_no_internet_connection))
        } catch (e: HttpException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
        }
    }

    override suspend fun addArticleToFavorites(slug: String): Resource<Article> {
        return try {
            val response =
                conduitApi.addArticleToFavorites(slug = slug)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val article = responseBody.article.toArticle()
                    Resource.Success(
                        data = article,
                        message = UiMessage.StringResource(R.string.article_added_to_favorites)
                    )
                } else {
                    Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                }
            } else {
                Resource.Error(message = ErrorUtils.parseErrorResponse(response.errorBody()))
            }
        } catch (e: IOException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_no_internet_connection))
        } catch (e: HttpException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
        }
    }

    override suspend fun removeArticleFromFavorites(slug: String): Resource<Article> {
        return try {
            val response =
                conduitApi.removeArticleFromFavorites(slug = slug)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val article = responseBody.article.toArticle()
                    Resource.Success(
                        data = article,
                        message = UiMessage.StringResource(R.string.article_removed_from_favorites)
                    )
                } else {
                    Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                }
            } else {
                Resource.Error(message = ErrorUtils.parseErrorResponse(response.errorBody()))
            }
        } catch (e: IOException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_no_internet_connection))
        } catch (e: HttpException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
        }
    }

    override suspend fun createArticle(
        title: String,
        description: String,
        body: String
    ): Resource<Article> {
        val createArticleRequest = CreateArticleRequest(
            CreateArticleDto(
                title = title,
                description = description,
                body = body
            )
        )
        return try {
            val response =
                conduitApi.createArticle(createArticleRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val article = responseBody.article.toArticle()
                    Resource.Success(
                        data = article,
                        message = UiMessage.StringResource(R.string.article_published_successfully)
                    )
                } else {
                    Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                }
            } else {
                Resource.Error(message = ErrorUtils.parseErrorResponse(response.errorBody()))
            }
        } catch (e: IOException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_no_internet_connection))
        } catch (e: HttpException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
        }
    }
}