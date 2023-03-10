package com.rahulghag.conduit.ui.auth.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulghag.conduit.domain.usecases.SignUpUseCase
import com.rahulghag.conduit.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onEvent(event: SignUpUiEvent) {
        when (event) {
            is SignUpUiEvent.EmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
            }
            is SignUpUiEvent.PasswordChanged -> {
                _uiState.update { it.copy(password = event.password) }
            }
            is SignUpUiEvent.UsernameChanged -> {
                _uiState.update { it.copy(username = event.username) }
            }
            SignUpUiEvent.SignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() = viewModelScope.launch {
        _uiState.update { it.copy(isLoading = true) }
        when (val result = signUpUseCase.invoke(
            email = uiState.value.email,
            password = uiState.value.password,
            username = uiState.value.username
        )) {
            is Resource.Success -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSignUpSuccessful = true
                    )
                }
            }
            is Resource.Error -> {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSignUpSuccessful = false,
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