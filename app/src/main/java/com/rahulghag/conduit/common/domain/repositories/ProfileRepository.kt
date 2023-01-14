package com.rahulghag.conduit.common.domain.repositories

import com.rahulghag.conduit.common.domain.models.Profile
import com.rahulghag.conduit.common.utils.Resource

interface ProfileRepository {
    suspend fun getUserProfile(username: String): Resource<Profile>

    suspend fun followUser(username: String): Resource<Profile>

    suspend fun unfollowUser(username: String): Resource<Profile>
}