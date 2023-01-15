package com.rahulghag.conduit.common.data.repositories

import com.rahulghag.conduit.R
import com.rahulghag.conduit.common.data.remote.ConduitApi
import com.rahulghag.conduit.common.domain.models.Profile
import com.rahulghag.conduit.common.domain.repositories.ProfileRepository
import com.rahulghag.conduit.common.utils.ErrorUtils
import com.rahulghag.conduit.common.utils.Resource
import com.rahulghag.conduit.common.utils.UiMessage
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val conduitApi: ConduitApi
) : ProfileRepository {
    override suspend fun getUserProfile(username: String): Resource<Profile> {
        return try {
            val response =
                conduitApi.getUserProfile(username = username)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val profile = responseBody.profile.toProfile()
                    Resource.Success(data = profile)
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

    override suspend fun followUser(username: String): Resource<Profile> {
        return try {
            val response =
                conduitApi.followUser(username = username)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val profile = responseBody.profile.toProfile()
                    Resource.Success(
                        data = profile,
                        message = UiMessage.StringResource(R.string.author_followed)
                    )
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

    override suspend fun unfollowUser(username: String): Resource<Profile> {
        return try {
            val response =
                conduitApi.unfollowUser(username = username)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val profile = responseBody.profile.toProfile()
                    Resource.Success(
                        data = profile,
                        message = UiMessage.StringResource(R.string.author_unfollowed)
                    )
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