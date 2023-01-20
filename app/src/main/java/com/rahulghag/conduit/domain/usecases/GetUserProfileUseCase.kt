package com.rahulghag.conduit.domain.usecases

import com.rahulghag.conduit.domain.models.Profile
import com.rahulghag.conduit.domain.repositories.ProfileRepository
import com.rahulghag.conduit.utils.Resource
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(): Resource<Profile> {
        return profileRepository.getUserProfile()
    }
}