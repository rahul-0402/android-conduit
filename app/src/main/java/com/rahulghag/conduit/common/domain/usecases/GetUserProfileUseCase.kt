package com.rahulghag.conduit.common.domain.usecases

import com.rahulghag.conduit.common.domain.models.Profile
import com.rahulghag.conduit.common.domain.repositories.ProfileRepository
import com.rahulghag.conduit.common.utils.Resource
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(username: String): Resource<Profile> {
        return profileRepository.getUserProfile(username = username)
    }
}