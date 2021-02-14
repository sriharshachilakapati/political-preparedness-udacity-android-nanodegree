package com.example.android.politicalpreparedness.election

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class ElectionsViewModel(private val repository: ElectionRepository) : ViewModel() {
    val allElections = repository.getAllElections()
    val savedElections = repository.getSavedElections()

    init {
        viewModelScope.launch { repository.refreshData() }
    }

    fun followElection(election: Election) = viewModelScope.launch {
        repository.followElection(election)
    }

    fun unFollowElection(election: Election) = viewModelScope.launch {
        repository.unFollowElection(election)
    }
}