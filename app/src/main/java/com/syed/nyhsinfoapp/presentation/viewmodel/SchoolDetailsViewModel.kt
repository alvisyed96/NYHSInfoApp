package com.syed.nyhsinfoapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syed.nyhsinfoapp.data.util.Resource
import com.syed.nyhsinfoapp.domain.usecase.GetSchoolDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolDetailsViewModel @Inject constructor(private val useCase: GetSchoolDetailsUseCase) : ViewModel() {
    private val _details: MutableLiveData<Resource> = MutableLiveData()
    val details: LiveData<Resource> = _details

    fun fetchDetails() = viewModelScope.launch {
        useCase.getDetails().collect {
            _details.postValue(it)
        }
    }
}