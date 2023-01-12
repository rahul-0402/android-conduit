package com.rahulghag.conduit.ui.articles.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.conduit.common.Resource
import com.rahulghag.conduit.domain.usecases.GetArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ArticleListUiState())
    val uiState: StateFlow<ArticleListUiState> = _uiState.asStateFlow()

    init {
        getArticleList()
    }

    fun onEvent(event: ArticleListUiEvent) {

    }

    private fun getArticleList() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        when (val result = getArticlesUseCase()) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        articles = result.data
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

    fun messageShown() {
        _uiState.update {
            it.copy(message = null)
        }
    }
}