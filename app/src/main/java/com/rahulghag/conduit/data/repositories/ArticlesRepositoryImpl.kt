package com.rahulghag.conduit.data.repositories

import com.rahulghag.conduit.R
import com.rahulghag.conduit.data.remote.ConduitApi
import com.rahulghag.conduit.domain.models.Article
import com.rahulghag.conduit.domain.repositories.ArticlesRepository
import com.rahulghag.conduit.utils.ErrorUtils
import com.rahulghag.conduit.utils.Resource
import com.rahulghag.conduit.utils.UiMessage
import retrofit2.HttpException
import java.io.IOException

class ArticlesRepositoryImpl(
    private val conduitApi: ConduitApi
) : ArticlesRepository {
    override suspend fun getArticleList(): Resource<List<Article>> {
        return try {
            val response =
                conduitApi.getArticleList()
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val articleList = responseBody.articles.map { it.toArticle() }
                    Resource.Success(data = articleList)
                } else {
                    Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                }
            } else {
                val errorMessages = ErrorUtils.parseErrorResponse(response.errorBody())
                if (errorMessages.isNullOrEmpty()) {
                    Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
                } else {
                    Resource.Error(message = UiMessage.DynamicMessage(errorMessages))
                }
            }
        } catch (e: IOException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_no_internet_connection))
        } catch (e: HttpException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
        }
    }
}