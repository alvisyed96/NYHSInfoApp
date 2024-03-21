package com.syed.nyhsinfoapp.domain.repository

import com.syed.nyhsinfoapp.data.util.Resource
import kotlinx.coroutines.flow.Flow

interface SchoolDetailsRepository {
    suspend fun getDetails(): Flow<Resource>
}