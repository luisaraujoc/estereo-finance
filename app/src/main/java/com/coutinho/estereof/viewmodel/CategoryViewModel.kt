package com.coutinho.estereof.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.coutinho.estereof.data.model.Category
import com.coutinho.estereof.data.repository.CategoryRepository
import com.coutinho.estereof.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Date

data class CategoryState(
    val isLoading: Boolean = true,
    val categories: List<Category> = emptyList(),
    val error: String? = null
)

class CategoryViewModel(
    private val categoryRepository: CategoryRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _categoryState = MutableStateFlow(CategoryState())
    val categoryState: StateFlow<CategoryState> = _categoryState.asStateFlow()

    private val _userMessage = MutableStateFlow<String?>(null)
    val userMessage: StateFlow<String?> = _userMessage.asStateFlow()

    private var loggedInUserId: Long? = null

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _categoryState.value = _categoryState.value.copy(isLoading = true)
            try {
                val user = userRepository.getAll().firstOrNull()?.firstOrNull()
                if (user != null) {
                    loggedInUserId = user.id
                    categoryRepository.getByUserId(user.id).collect { categories ->
                        _categoryState.value = _categoryState.value.copy(
                            categories = categories,
                            isLoading = false
                        )
                    }
                } else {
                    _categoryState.value = _categoryState.value.copy(
                        isLoading = false,
                        error = "Nenhum usuário logado encontrado."
                    )
                }
            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(
                    isLoading = false,
                    error = "Erro ao carregar categorias: ${e.message}"
                )
            }
        }
    }

    fun addCategory(name: String) {
        if (loggedInUserId == null) {
            _userMessage.value = "Erro: Usuário não logado."
            return
        }

        viewModelScope.launch {
            try {
                val newCategory = Category(
                    userId = loggedInUserId!!,
                    name = name,
                    createdAt = Date(),
                    updatedAt = Date()
                )
                categoryRepository.insert(newCategory)
                _userMessage.value = "Categoria adicionada com sucesso!"
            } catch (e: Exception) {
                _userMessage.value = "Erro ao adicionar categoria: ${e.message}"
            }
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            try {
                categoryRepository.delete(category)
                _userMessage.value = "Categoria excluída com sucesso."
            } catch (e: Exception) {
                _userMessage.value = "Erro ao excluir categoria: ${e.message}"
            }
        }
    }

    fun clearUserMessage() {
        _userMessage.value = null
    }
}