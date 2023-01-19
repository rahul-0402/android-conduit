package com.rahulghag.conduit.ui.articles.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.conduit.domain.usecases.GetArticleUseCase
import com.rahulghag.conduit.domain.usecases.ToggleFavoriteArticleUseCase
import com.rahulghag.conduit.domain.usecases.ToggleFollowUserUseCase
import com.rahulghag.conduit.utils.Constants.NAV_ARG_SLUG
import com.rahulghag.conduit.utils.Resource
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
    private val toggleFollowUserUseCase: ToggleFollowUserUseCase,
    private val toggleFavoriteArticleUseCase: ToggleFavoriteArticleUseCase
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
            ArticleDetailsUiEvent.ToggleFavoriteArticle -> {
                toggleFavoriteArticle()
            }
        }
    }

    private fun getArticle() = viewModelScope.launch {
        _uiState.update {
            it.copy(isLoading = true)
        }
        when (val result = getArticleUseCase(slug)) {
            is Resource.Success -> {
                result.data?.let { article ->
                    _uiState.update {
                        it.copy(
                            authorName = article.author.username,
                            isFollowingAuthor = article.author.isFollowing,
                            publishedDate = article.formattedDate ?: "",
                            title = article.title,
                            body = article.body,
                            isFavorite = article.isFavorite,
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

    private fun toggleFavoriteArticle() = viewModelScope.launch {
        _uiState.value.isFavorite?.let { isFavorite ->
            when (val result = toggleFavoriteArticleUseCase.invoke(
                isFavorite = isFavorite,
                slug = slug
            )) {
                is Resource.Success -> {
                    result.data?.let { article ->
                        _uiState.update {
                            it.copy(
                                isFavorite = article.isFavorite,
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