package com.shihab.smartpossearch.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shihab.smartpossearch.models.Product
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val allProducts = listOf(
        Product(1, "Apple MacBook Pro 16", "SKU-001"),
        Product(2, "Apple iPhone 15 Pro", "SKU-002"),
        Product(3, "Samsung Galaxy S24 Ultra", "SKU-003"),
        Product(4, "Sony Noise Cancelling Headphones", "SKU-004"),
        Product(5, "Dell XPS 15 Laptop", "SKU-005"),
        Product(6, "Logitech MX Master 3S Mouse", "SKU-006"),
        Product(7, "Keychron Mechanical Keyboard", "SKU-007")
    )

    var searchQuery by mutableStateOf("")
        private set
    var searchResults by mutableStateOf(allProducts)
        private set
    var isLoading by mutableStateOf(false)
        private set

    private var searchJob: Job? = null

    fun onSearchQueryChanged(query: String) {
        searchQuery = query

        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            if (query.isBlank()) {
                searchResults = allProducts
                return@launch
            }

            delay(300)

            isLoading = true
            searchResults = performFakeApiSearch(query)
            isLoading = false
        }
    }

    private suspend fun performFakeApiSearch(query: String): List<Product> {
        delay(500)

        return allProducts.filter {
            it.name.contains(
                query,
                ignoreCase = true
            ) ||
                    it.sku.contains(
                        query,
                        ignoreCase = true
                    )
        }
    }
}