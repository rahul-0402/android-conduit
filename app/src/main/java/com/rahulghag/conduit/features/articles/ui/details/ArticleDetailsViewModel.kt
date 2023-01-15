package com.rahulghag.conduit.features.articles.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.conduit.common.domain.usecases.ToggleFollowUserUseCase
import com.rahulghag.conduit.common.utils.Constants.NAV_ARG_SLUG
import com.rahulghag.conduit.common.utils.Resource
import com.rahulghag.conduit.features.articles.domain.usecases.GetArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getArticleUseCase: GetArticleUseCase,
    private val toggleFollowUserUseCase: ToggleFollowUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArticleDetailsUiState())
    val uiState: StateFlow<ArticleDetailsUiState> = _uiState.asStateFlow()

    private val slug: String = checkNotNull(savedStateHandle[NAV_ARG_SLUG])

    init {
        getArticle()
    }

    fun onEvent(event: ArticleDetailsUiEvent) {
        when (event) {
            is ArticleDetailsUiEvent.ToggleFollowUser -> {
                toggleFollowUser()
            }
        }
    }

    private fun getArticle() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        when (val result = getArticleUseCase(slug)) {
            is Resource.Success -> {
                result.data?.let { article ->
                    _uiState.update {
                        it.copy(
                            title = article.title,
                            body = article.body,
                            publishedDate = article.createdAt,
                            authorName = article.author.username,
                            isFollowingAuthor = article.author.isFollowing,
                            isLoading = false
                        )
                    }
                }
            }
            is Resource.Error -> {
                _uiState.update {
                    it.copy(
                        message = result.message,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun toggleFollowUser() = viewModelScope.launch {
        _uiState.value.isFollowingAuthor?.let { isFollowing ->
            when (val result = toggleFollowUserUseCase.invoke(
                isFollowing = isFollowing,
                username = _uiState.value.authorName
            )) {
                is Resource.Success -> {
                    result.data?.let { authorProfile ->
                        _uiState.update {
                            it.copy(
                                isFollowingAuthor = authorProfile.isFollowing,
                                message = result.message
                            )
                        }
                    }
                }
                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            message = result.message
                        )
                    }
                }
            }
        }
    }

    fun messageShown() {
        _uiState.update {
            it.copy(message = null)
        }
    }

    companion object {
        private const val TAG = "ArticleDetailsViewModel"
    }
}