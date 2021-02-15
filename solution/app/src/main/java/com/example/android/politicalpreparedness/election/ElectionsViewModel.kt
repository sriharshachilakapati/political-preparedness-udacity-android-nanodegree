package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ElectionsViewModel(private val repository: ElectionRepository) : ViewModel() {
    val allElections get() = repository.getAllElections()
    val savedElections get() = repository.getSavedElections()

    init {
        viewModelScope.launch { repository.refreshData() }
    }
}