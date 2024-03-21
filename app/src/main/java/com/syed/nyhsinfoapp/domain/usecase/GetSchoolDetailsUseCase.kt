package com.syed.nyhsinfoapp.domain.usecase

import com.syed.nyhsinfoapp.domain.repository.SchoolDetailsRepository
import javax.inject.Inject

class GetSchoolDetailsUseCase @Inject constructor(private val repository: SchoolDetailsRepository) {
    suspend fun getDetails() = repository.getDetails()
}