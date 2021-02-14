package com.example.android.politicalpreparedness.election

import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class ElectionRepository(private val dao: ElectionDao) {
    fun getAllElections() = dao.getAllElections()
    fun getSavedElections() = dao.getAllSavedElections()

    suspend fun followElection(election: Election) = dao.saveElection(election)
    suspend fun unFollowElection(election: Election) = dao.deleteSavedElection(election)

    suspend fun refreshData() = withContext(Dispatchers.IO) {
        listOf(
                async {
                    dao.deletePastSavedElections()
                    dao.deletePastElections()
                },
                async { refreshElectionsData() }
        ).awaitAll()
    }

    private suspend fun refreshElectionsData() = try {
        val response = CivicsApi.retrofitService.getElections()
        val elections = response.body()?.elections

        elections?.forEach { dao.insert(it) }
    } catch (e: HttpException) {
        e.printStackTrace()
    }
}