package com.rahulghag.conduit.domain.usecases

import com.rahulghag.conduit.domain.models.Profile
import com.rahulghag.conduit.domain.repositories.ProfileRepository
import com.rahulghag.conduit.utils.Resource
import javax.inject.Inject

class ToggleFollowUserUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(isFollowing: Boolean, username: String): Resource<Profile> {
        return if (isFollowing) {
            profileRepository.unfollowUser(username = username)
        } else {
            profileRepository.followUser(username = username)
        }
    }
}