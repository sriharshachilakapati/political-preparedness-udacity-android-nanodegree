package com.example.android.politicalpreparedness.election

import android.content.Context
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.util.simpleViewModelFactory

fun electionsViewModelFactory(context: Context) = simpleViewModelFactory {
    val database = ElectionDatabase.getInstance(context)
    val repository = ElectionRepository(database.electionDao)

    ElectionsViewModel(repository)
}