package com.example.android.politicalpreparedness.election

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election

class VoterInfoViewModelFactory(
        private val context: Context,
        private val election: Election
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val database = ElectionDatabase.getInstance(context)
        val repository = ElectionRepository(database.electionDao)

        val followString = context.getString(R.string.follow_election)
        val unFollowString = context.getString(R.string.unfollow_election)

        return modelClass.getConstructor(
                ElectionRepository::class.java,
                Election::class.java,
                String::class.java,
                String::class.java
        ).newInstance(repository, election, followString, unFollowString)
    }
}