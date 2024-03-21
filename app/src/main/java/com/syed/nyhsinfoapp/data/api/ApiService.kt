package com.syed.nyhsinfoapp.data.api

import com.syed.nyhsinfoapp.data.model.ApiResponse
import com.syed.nyhsinfoapp.data.util.END_POINT
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET(END_POINT)
    suspend fun getDetails(): Response<ApiResponse>
}