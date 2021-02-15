package com.example.android.politicalpreparedness.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <reified T : ViewModel> simpleViewModelFactory(noinline provider: () -> T) =
        SimpleViewModelFactoryImpl(T::class.java, provider)

class SimpleViewModelFactoryImpl(
        private val `class`: Class<*>,
        private val provider: () -> ViewModel
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (!modelClass.isAssignableFrom(`class`)) {
            throw IllegalArgumentException("ViewModel type is non assignable")
        }

        @Suppress("UNCHECKED_CAST")
        return provider() as T
    }
}