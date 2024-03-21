package com.syed.nyhsinfoapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.syed.nyhsinfoapp.domain.usecase.GetSchoolDetailsUseCase
import javax.inject.Inject

class SchoolDetailsFactory @Inject constructor(private val useCase: GetSchoolDetailsUseCase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SchoolDetailsViewModel::class.java)) {
            return SchoolDetailsViewModel(useCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}