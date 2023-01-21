package com.rahulghag.conduit.data.repositories

import com.rahulghag.conduit.R
import com.rahulghag.conduit.data.remote.ConduitApi
import com.rahulghag.conduit.domain.models.Profile
import com.rahulghag.conduit.domain.repositories.PreferencesManager
import com.rahulghag.conduit.domain.repositories.ProfileRepository
import com.rahulghag.conduit.utils.ErrorUtils
import com.rahulghag.conduit.utils.Resource
import com.rahulghag.conduit.utils.UiMessage
import kotlinx.coroutines.flow.first
import retrofit2.HttpException
import java.io.IOException

class ProfileRepositoryImpl(
    private val conduitApi: ConduitApi,
    private val preferencesManager: PreferencesManager
) : ProfileRepository {
    override suspend fun getUserProfile(): Resource<Profile> {
        return try {
            val response =
                conduitApi.getUserProfile(preferencesManager.getUsername().first())
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val profile = responseBody.profile.toProfile()
                    Resource.Success(data = profile)
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
                Resource.Error(message = ErrorUtils.parseErrorResponse(response.errorBody()))
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
                Resource.Error(message = ErrorUtils.parseErrorResponse(response.errorBody()))
            }
        } catch (e: IOException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_no_internet_connection))
        } catch (e: HttpException) {
            Resource.Error(message = UiMessage.StringResource(R.string.error_something_went_wrong))
        }
    }
}