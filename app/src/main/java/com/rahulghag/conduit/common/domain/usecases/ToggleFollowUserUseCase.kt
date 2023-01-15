package com.rahulghag.conduit.common.domain.usecases

import com.rahulghag.conduit.common.domain.models.Profile
import com.rahulghag.conduit.common.domain.repositories.ProfileRepository
import com.rahulghag.conduit.common.utils.Resource
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