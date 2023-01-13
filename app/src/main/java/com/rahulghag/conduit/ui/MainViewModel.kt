package com.rahulghag.conduit.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahulghag.conduit.domain.usecases.GetUserAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getUserAuthStateUseCase: GetUserAuthStateUseCase
) : ViewModel() {
    private val _isUserAuthenticated = MutableLiveData<Boolean>()
    val isUserAuthenticated: LiveData<Boolean> = _isUserAuthenticated

    init {
        _isUserAuthenticated.postValue(getUserAuthStateUseCase.invoke())
    }
}