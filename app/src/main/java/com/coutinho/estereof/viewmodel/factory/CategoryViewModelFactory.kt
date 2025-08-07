package com.coutinho.estereof.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.coutinho.estereof.data.repository.CategoryRepository
import com.coutinho.estereof.data.repository.UserRepository
import com.coutinho.estereof.viewmodel.CategoryViewModel

class CategoryViewModelFactory(
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewModel(categoryRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}