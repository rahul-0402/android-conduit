package com.rahulghag.conduit.features.articles.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.conduit.common.domain.usecases.FollowUserUseCase
import com.rahulghag.conduit.common.domain.usecases.UnfollowUserUseCase
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
    private val followUserUseCase: FollowUserUseCase,
    private val unfollowUserUseCase: UnfollowUserUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArticleDetailsUiState())
    val uiState: StateFlow<ArticleDetailsUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(slug = checkNotNull(savedStateHandle[NAV_ARG_SLUG])) }
        getArticle()
    }

    fun onEvent(event: ArticleDetailsUiEvent) {
        when (event) {
            is ArticleDetailsUiEvent.ToggleFollowUserState -> {
                toggleFollowUserState()
            }
        }
    }

    private fun getArticle() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        when (val result = getArticleUseCase(uiState.value.slug)) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        article = result.data
                    )
                }
            }
            is Resource.Error -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        message = result.message
                    )
                }
            }
        }
    }

    private fun toggleFollowUserState() = viewModelScope.launch {
        _uiState.value.article?.let { article ->
            val isFollowing = article.author.isFollowing
            val result = if (isFollowing) {
                unfollowUserUseCase.invoke(article.author.username)
            } else {
                followUserUseCase.invoke(article.author.username)
            }
            when (result) {
                is Resource.Success -> Unit
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