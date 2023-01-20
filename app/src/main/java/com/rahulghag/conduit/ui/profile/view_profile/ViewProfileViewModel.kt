package com.rahulghag.conduit.ui.profile.view_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.conduit.domain.usecases.GetArticlesUseCase
import com.rahulghag.conduit.domain.usecases.GetUserProfileUseCase
import com.rahulghag.conduit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewProfileViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getArticlesUseCase: GetArticlesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ViewProfileUiState())
    val uiState: StateFlow<ViewProfileUiState> = _uiState.asStateFlow()

    init {
        // FIXME: Make parallel API calls - https://amitshekhar.me/blog/parallel-multiple-network-calls-using-kotlin-coroutines
        getUserProfile()
        getArticleList()
    }

    fun onEvent(event: ViewProfileUiEvent) {

    }

    private fun getUserProfile() = viewModelScope.launch {
        _uiState.update {
            it.copy(isLoading = true)
        }
        // FIXME: Replace hardcoded user name with saved username from sp
        when (val result = getUserProfileUseCase(username = "Rahul G")) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        profile = result.data,
                        isLoading = false
                    )
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

    private fun getArticleList() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        when (val result = getArticlesUseCase()) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        articles = result.data,
                        isLoading = false
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