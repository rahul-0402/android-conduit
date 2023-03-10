package com.rahulghag.conduit.ui.profile.view_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.conduit.domain.usecases.GetArticlesUseCase
import com.rahulghag.conduit.domain.usecases.GetUserProfileUseCase
import com.rahulghag.conduit.domain.usecases.LogoutUserUseCase
import com.rahulghag.conduit.utils.ArticleSortType
import com.rahulghag.conduit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getArticlesUseCase: GetArticlesUseCase,
    private val logoutUserUseCase: LogoutUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ViewProfileUiState())
    val uiState: StateFlow<ViewProfileUiState> = _uiState.asStateFlow()

    init {
        getUserProfileWithArticles()
    }

    fun onEvent(event: ViewProfileUiEvent) {
        when (event) {
            ViewProfileUiEvent.ShowMyArticles -> {
                _uiState.update {
                    it.copy(
                        selectedTabPosition = 0,
                        articles = _uiState.value.myArticles
                    )
                }
            }
            ViewProfileUiEvent.ShowFavoritedArticles -> {
                _uiState.update {
                    it.copy(
                        selectedTabPosition = 1,
                        articles = _uiState.value.favoritedArticles
                    )
                }
            }
            ViewProfileUiEvent.Logout -> {
                logoutUser()
            }
        }
    }

    private fun getUserProfileWithArticles() = viewModelScope.launch {
        _uiState.update {
            it.copy(isLoading = true)
        }
        val userProfileDeferred = async { getUserProfileUseCase() }
        val articlesByUsernameDeferred = async {
            getArticlesUseCase(articleSortType = ArticleSortType.BY_USERNAME)
        }
        val favoritedArticlesByUsernameDeferred = async {
            getArticlesUseCase(articleSortType = ArticleSortType.FAVORITED_BY_USERNAME)
        }

        val userProfile = userProfileDeferred.await()
        val articlesByUsername = articlesByUsernameDeferred.await()
        val favoritedArticlesByUsername = favoritedArticlesByUsernameDeferred.await()
        val results = listOf(userProfile, articlesByUsername, favoritedArticlesByUsername)
        results.forEach { result ->
            if (result is Resource.Error) {
                _uiState.update {
                    it.copy(
                        message = result.message,
                        isLoading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        profile = userProfile.data,
                        myArticles = articlesByUsername.data,
                        favoritedArticles = favoritedArticlesByUsername.data,
                        articles = articlesByUsername.data,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun logoutUser() = viewModelScope.launch {
        logoutUserUseCase.invoke()
    }

    fun messageShown() {
        _uiState.update {
            it.copy(message = null)
        }
    }
}