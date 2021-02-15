package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VoterInfoViewModel(
        private val electionRepository: ElectionRepository,
        private val election: Election,
        private val followString: String,
        private val unFollowString: String
) : ViewModel() {

    private val _buttonText = MutableLiveData<String>()
    val buttonText: LiveData<String> = _buttonText

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val string = if (electionRepository.isElectionSaved(election)) {
                unFollowString
            } else {
                followString
            }

            _buttonText.postValue(string)
        }
    }

    fun toggleFollowElection() = viewModelScope.launch(Dispatchers.IO) {
        if (electionRepository.isElectionSaved(election)) {
            unFollowElection(election)
            _buttonText.postValue(followString)
        } else {
            followElection(election)
            _buttonText.postValue(unFollowString)
        }
    }

    private suspend fun followElection(election: Election) {
        electionRepository.followElection(election)
    }

    private suspend fun unFollowElection(election: Election) {
        electionRepository.unFollowElection(election)
    }

    //TODO: Add live data to hold voter info

    //TODO: Add var and methods to populate voter info

    //TODO: Add var and methods to support loading URLs

    //TODO: Add var and methods to save and remove elections to local database
    //TODO: cont'd -- Populate initial state of save button to reflect proper action based on election saved status

    /**
     * Hint: The saved state can be accomplished in multiple ways. It is directly related to how elections are saved/removed from the database.
     */

}