package com.rahulghag.conduit.data.repositories

import com.rahulghag.conduit.R
import com.rahulghag.conduit.common.ErrorUtils
import com.rahulghag.conduit.common.Resource
import com.rahulghag.conduit.common.UiMessage
import com.rahulghag.conduit.data.remote.ConduitApi
import com.rahulghag.conduit.data.remote.TokenManager
import com.rahulghag.conduit.data.remote.dtos.request.SignInDto
import com.rahulghag.conduit.data.remote.dtos.request.SignInRequest
import com.rahulghag.conduit.data.remote.dtos.request.SignUpDto
import com.rahulghag.conduit.data.remote.dtos.request.SignUpRequest
import com.rahulghag.conduit.domain.models.User
import com.rahulghag.conduit.domain.repositories.AuthRepository
import retrofit2.HttpException
import java.io.IOException

class AuthRepositoryImpl(
    private val conduitApi: ConduitApi,
    private val tokenManager: TokenManager
) : AuthRepository {
    override suspend fun signIn(email: String, password: String): Resource<User> {
        val signInRequest = SignInRequest(SignInDto(email = email, password = password))
        return try {
            val response =
                conduitApi.signIn(signInRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val user = responseBody.userDto.toUser()
                    tokenManager.saveToken(user.token)
                    Resource.Success(data = user)
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

    override suspend fun signUp(email: String, password: String, username: String): Resource<User> {
        val signUpRequest =
            SignUpRequest(SignUpDto(email = email, password = password, username = username))
        return try {
            val response =
                conduitApi.signUp(signUpRequest)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    val user = responseBody.userDto.toUser()
                    tokenManager.saveToken(user.token)
                    Resource.Success(data = user)
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