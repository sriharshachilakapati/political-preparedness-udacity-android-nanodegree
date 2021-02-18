package com.example.android.politicalpreparedness.election

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import com.example.android.politicalpreparedness.network.models.toFormattedString
import kotlinx.coroutines.*

class VoterInfoViewModel(
        private val electionRepository: ElectionRepository,
        private val election: Election,
        private val followString: String,
        private val unFollowString: String
) : ViewModel() {

    private val _buttonText = MutableLiveData<String>()
    val buttonText: LiveData<String> = _buttonText

    private val voterInfo = MutableLiveData<VoterInfoResponse>()

    val hasElectionInformation: LiveData<Boolean> = Transformations.map(voterInfo) {
        listOfNotNull(
                it?.state?.firstOrNull()?.electionAdministrationBody?.ballotInfoUrl,
                it?.state?.firstOrNull()?.electionAdministrationBody?.votingLocationFinderUrl
        ).size == 2
    }

    val hasMailingAddress: LiveData<Boolean> = Transformations.map(voterInfo) {
        it?.state?.firstOrNull()?.electionAdministrationBody?.correspondenceAddress != null
    }

    val mailingAddress: LiveData<String> = Transformations.map(voterInfo) {
        it?.state?.firstOrNull()?.electionAdministrationBody?.correspondenceAddress?.toFormattedString()
                ?: ""
    }

    val votingLocationFinderURL: LiveData<String> = Transformations.map(voterInfo) {
        it?.state?.firstOrNull()?.electionAdministrationBody?.votingLocationFinderUrl
    }

    val ballotInformationURL: LiveData<String> = Transformations.map(voterInfo) {
        it?.state?.firstOrNull()?.electionAdministrationBody?.ballotInfoUrl
    }

    init {
        viewModelScope.launch {
            listOf(
                    async { checkAndSetToggleFollowButtonText() },
                    async { fetchVotersInfo() }
            ).awaitAll()
        }
    }

    private suspend fun checkAndSetToggleFollowButtonText() = withContext(Dispatchers.IO) {
        val string = if (electionRepository.isElectionSaved(election)) {
            unFollowString
        } else {
            followString
        }

        _buttonText.postValue(string)
    }

    private suspend fun fetchVotersInfo() = withContext(Dispatchers.IO) {
        try {
            val response = CivicsApi.retrofitService.getVoterInfo(election.division.toFormattedString(), election.id)

            if (response.isSuccessful) {
                voterInfo.postValue(response.body()!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
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
}