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

    // ফেক ডাটাবেজ (POS সিস্টেমে যেমন হাজার হাজার প্রোডাক্ট থাকে)
    private val allProducts = listOf(
        Product(1, "Apple MacBook Pro 16", "SKU-001"),
        Product(2, "Apple iPhone 15 Pro", "SKU-002"),
        Product(3, "Samsung Galaxy S24 Ultra", "SKU-003"),
        Product(4, "Sony Noise Cancelling Headphones", "SKU-004"),
        Product(5, "Dell XPS 15 Laptop", "SKU-005"),
        Product(6, "Logitech MX Master 3S Mouse", "SKU-006"),
        Product(7, "Keychron Mechanical Keyboard", "SKU-007")
    )

    // UI-কে আপডেট করার জন্য স্টেটস
    var searchQuery by mutableStateOf("")
        private set
    var searchResults by mutableStateOf(allProducts)
        private set
    var isLoading by mutableStateOf(false)
        private set

    // ডিবাইন্স (Debounce) লজিকের জন্য Coroutine Job ট্র্যাকার
    private var searchJob: Job? = null

    // ইউজার যখন সার্চ বারে টাইপ করবে, তখন এই ফাংশনটি কল হবে
    fun onSearchQueryChanged(query: String) {
        searchQuery = query

        // ম্যাজিক ১: Job Cancellation
        // ইউজার দ্রুত টাইপ করলে আগের রিকোয়েস্টগুলো সাথে সাথে ক্যানসেল হয়ে যাবে!
        searchJob?.cancel()

        // নতুন Job শুরু করা
        searchJob = viewModelScope.launch {
            if (query.isBlank()) {
                searchResults = allProducts
                return@launch
            }

            // ম্যাজিক ২: Debounce Delay
            // ইউজার টাইপ থামানোর পর ঠিক ৩০০ মিলিসেকেন্ড অপেক্ষা করবে
            delay(300)

            // ৩০০ms পার হয়ে গেলে আমরা ফেক এপিআই কল করব
            isLoading = true
            searchResults = performFakeApiSearch(query)
            isLoading = false
        }
    }

    // ফেক এপিআই কল (সার্ভার থেকে ডাটা আনতে যেমন সময় লাগে)
    private suspend fun performFakeApiSearch(query: String): List<Product> {
        delay(500) // নেটওয়ার্ক ডিলে বা লোডিং সিমুলেট করার জন্য ৫০০ms থামালাম

        return allProducts.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.sku.contains(query, ignoreCase = true)
        }
    }
}