package com.rahulghag.conduit.ui.profile.view_profile

import com.rahulghag.conduit.domain.models.Article
import com.rahulghag.conduit.domain.models.Profile
import com.rahulghag.conduit.utils.UiMessage

data class ViewProfileUiState(
    val profile: Profile? = null,
    val selectedTabPosition: Int = 0,
    val myArticles: List<Article>? = emptyList(),
    val favoritedArticles: List<Article>? = emptyList(),
    val articles: List<Article>? = emptyList(),
    val isLoading: Boolean = false,
    val message: UiMessage? = null
)