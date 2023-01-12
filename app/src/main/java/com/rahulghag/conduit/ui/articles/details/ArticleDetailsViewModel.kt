package com.rahulghag.conduit.ui.articles.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.conduit.common.Resource
import com.rahulghag.conduit.domain.usecases.GetArticleUseCase
import com.rahulghag.conduit.domain.usecases.GetUserAuthStateUseCase
import com.rahulghag.conduit.ui.utils.Constants.NAV_ARG_SLUG
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
    private val getUserAuthStateUseCase: GetUserAuthStateUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArticleDetailsUiState())
    val uiState: StateFlow<ArticleDetailsUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(slug = checkNotNull(savedStateHandle[NAV_ARG_SLUG])) }
        getArticle()
    }

    fun onEvent(event: ArticleDetailsUiEvent) {
        when (event) {
            ArticleDetailsUiEvent.FollowAuthor -> {
                followAuthor()
            }
            ArticleDetailsUiEvent.UnfollowAuthor -> {
                unfollowAuthor()
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

    fun isUserAuthenticated(): Boolean {
        return getUserAuthStateUseCase.invoke()
    }

    private fun followAuthor() {

    }

    private fun unfollowAuthor() {

    }

    fun messageShown() {
        _uiState.update {
            it.copy(message = null)
        }
    }
}