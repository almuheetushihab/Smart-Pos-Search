# Smart POS Search 🔍

**Smart POS Search** is a modern Android application built with **Jetpack Compose**. It demonstrates a high-performance product searching mechanism specifically designed for Point of Sale (POS) systems where speed and efficiency are critical.

## 🌟 Why This App?
In a typical POS environment, searching through thousands of products can be resource-intensive. This app implements **Search Debouncing**, a technique that optimizes performance by reducing unnecessary processing and API calls, ensuring a smooth user experience even with large datasets.

## ✨ Key Features
- **Real-time Search:** Instantly find products by their **Name** or **SKU** code.
- **Search Debouncing:** The search operation triggers only after the user stops typing (300ms delay), saving CPU and memory resources.
- **Asynchronous Loading:** Uses a fake API simulation to demonstrate how to handle background data fetching.
- **Visual Feedback:** Includes a `CircularProgressIndicator` for loading states and a friendly "No products found!" message for empty results.
- **Material 3 UI:** A clean, modern interface following the latest Android design guidelines.

## 🛠️ Tech Stack
- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/compose) (Declarative UI)
- **Architecture:** MVVM (Model-View-ViewModel)
- **Concurrency:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- **State Management:** Compose State (`mutableStateOf`)

## 🏗️ Architecture & Logic
The project follows the **MVVM** pattern to ensure clean code separation:

1.  **Model:** `Product.kt` defines the data structure (ID, Name, SKU).
2.  **ViewModel:** `SearchViewModel.kt` handles the business logic. It manages the search state and implements the debouncing logic using Coroutine Jobs.
3.  **View (UI):** `SearchScreen.kt` reacts to the ViewModel's state, automatically updating the UI as the data changes.

### Search Debouncing Logic:
```kotlin
searchJob?.cancel() // Cancel previous job if user is still typing
searchJob = viewModelScope.launch {
    delay(300) // Wait for 300ms of inactivity
    isLoading = true
    searchResults = performFakeApiSearch(query)
    isLoading = false
}
```

## 📁 Project Structure
- `models/`: Data classes and entities.
- `viewmodel/`: Logic for handling search queries and states.
- `ui/screens/`: Composable functions for the UI layout.
- `MainActivity.kt`: The entry point of the application.

## 🚀 Getting Started
1. Clone the repository or open the project in **Android Studio**.
2. Sync the project with Gradle files.
3. Run the app on an emulator or physical device.
4. Start typing in the search bar (e.g., "MacBook", "iPhone", or "SKU-001") to see the smart search in action.

---
Developed with ❤️ by **Shihab**
