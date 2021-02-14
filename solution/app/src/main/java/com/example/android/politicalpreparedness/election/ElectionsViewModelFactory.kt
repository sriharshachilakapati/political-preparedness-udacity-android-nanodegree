package com.example.android.politicalpreparedness.election

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.database.ElectionDatabase

class ElectionsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val database = ElectionDatabase.getInstance(context)
        val repository = ElectionRepository(database.electionDao)

        return modelClass.getConstructor(ElectionRepository::class.java).newInstance(repository)
    }
}