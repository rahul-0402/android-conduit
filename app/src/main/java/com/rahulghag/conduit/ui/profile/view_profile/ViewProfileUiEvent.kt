package com.rahulghag.conduit.ui.profile.view_profile

sealed class ViewProfileUiEvent {
    object ShowMyArticles : ViewProfileUiEvent()
    object ShowFavoritedArticles : ViewProfileUiEvent()
    object Logout : ViewProfileUiEvent()
}