package com.syed.nyhsinfoapp.data.util

import com.syed.nyhsinfoapp.data.model.ApiResponse

sealed class Resource {
    class Success(val response: ApiResponse) : Resource()
    class Error(val message: String) : Resource()
    object Loading : Resource()
}