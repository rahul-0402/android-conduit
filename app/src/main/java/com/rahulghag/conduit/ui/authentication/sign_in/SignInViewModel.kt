package com.rahulghag.conduit.ui.authentication.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.conduit.common.Resource
import com.rahulghag.conduit.domain.usecases.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    fun onEvent(event: SignInUiEvent) {
        when (event) {
            is SignInUiEvent.EmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
            }
            is SignInUiEvent.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }
            SignInUiEvent.SignIn -> {
                signIn()
            }
        }
    }

    private fun signIn() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        when (val result = signInUseCase.invoke(
            email = uiState.value.email,
            password = uiState.value.password
        )) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSignInSuccessful = true
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

    companion object {
        private const val TAG = "SignInViewModel"
    }
}