package com.rahulghag.conduit.domain.repositories

import com.rahulghag.conduit.domain.models.Profile
import com.rahulghag.conduit.utils.Resource

interface ProfileRepository {
    suspend fun getUserProfile(): Resource<Profile>

    suspend fun followUser(username: String): Resource<Profile>

    suspend fun unfollowUser(username: String): Resource<Profile>
}