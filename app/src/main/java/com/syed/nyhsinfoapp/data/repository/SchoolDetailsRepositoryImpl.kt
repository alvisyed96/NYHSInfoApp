package com.syed.nyhsinfoapp.data.repository

import com.syed.nyhsinfoapp.data.api.ApiService
import com.syed.nyhsinfoapp.data.util.Resource
import com.syed.nyhsinfoapp.domain.repository.SchoolDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SchoolDetailsRepositoryImpl @Inject constructor(private val apiService: ApiService) : SchoolDetailsRepository {
    override suspend fun getDetails(): Flow<Resource> {
        return flow {
            try {
                emit(Resource.Loading)
                val response = apiService.getDetails()
                if (response.isSuccessful && response.body() != null) {
                    emit(Resource.Success(response.body()!!))
                } else {
                    emit(Resource.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e.message.toString()))
            }
        }
    }
}