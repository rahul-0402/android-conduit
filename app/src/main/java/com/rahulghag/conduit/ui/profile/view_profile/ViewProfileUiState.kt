package com.rahulghag.conduit.ui.profile.view_profile

import com.rahulghag.conduit.domain.models.Profile
import com.rahulghag.conduit.utils.UiMessage

data class ViewProfileUiState(
    val profile: Profile? = null,
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)
