package com.rahulghag.conduit.ui.articles.create_article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.conduit.domain.usecases.CreateArticleUseCase
import com.rahulghag.conduit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateArticleViewModel @Inject constructor(
    private val createArticleUseCase: CreateArticleUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CreateArticleUiState())
    val uiState: StateFlow<CreateArticleUiState> = _uiState.asStateFlow()

    fun onEvent(event: CreateArticleUiEvent) {
        when (event) {
            is CreateArticleUiEvent.TitleChanged -> {
                _uiState.update { it.copy(title = event.title) }
            }
            is CreateArticleUiEvent.DescriptionChanged -> {
                _uiState.update { it.copy(description = event.description) }
            }
            is CreateArticleUiEvent.ArticleBodyChanged -> {
                _uiState.update { it.copy(articleBody = event.articleBody) }
            }
            CreateArticleUiEvent.PublishArticle -> {
                createArticle()
            }
        }
    }

    private fun createArticle() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        when (val result = createArticleUseCase(
            title = _uiState.value.title,
            description = _uiState.value.description,
            body = _uiState.value.articleBody,
        )) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        isArticlePublished = true,
                        isLoading = false,
                        message = result.message
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
        _uiState.update { it.copy(message = null) }
    }

    companion object {
        private const val TAG = "CreateArticleViewModel"
    }
}