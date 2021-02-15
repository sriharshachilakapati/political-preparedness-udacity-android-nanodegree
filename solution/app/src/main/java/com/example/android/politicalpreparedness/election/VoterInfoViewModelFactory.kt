package com.example.android.politicalpreparedness.election

import android.content.Context
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.util.simpleViewModelFactory

fun voterInfoViewModelFactory(context: Context, election: Election) = simpleViewModelFactory {
    val database = ElectionDatabase.getInstance(context)
    val repository = ElectionRepository(database.electionDao)

    val followString = context.getString(R.string.follow_election)
    val unFollowString = context.getString(R.string.unfollow_election)

    VoterInfoViewModel(repository, election, followString, unFollowString)
}