package com.syed.nyhsinfoapp.presentation.di

import com.syed.nyhsinfoapp.data.api.ApiService
import com.syed.nyhsinfoapp.data.repository.SchoolDetailsRepositoryImpl
import com.syed.nyhsinfoapp.data.util.BASE_URL
import com.syed.nyhsinfoapp.domain.repository.SchoolDetailsRepository
import com.syed.nyhsinfoapp.domain.usecase.GetSchoolDetailsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): SchoolDetailsRepository {
        return SchoolDetailsRepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideUsecase(repository: SchoolDetailsRepository): GetSchoolDetailsUseCase {
        return GetSchoolDetailsUseCase(repository)
    }
}