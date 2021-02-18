package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.SavedElection
import java.time.Instant
import java.util.*

@Dao
interface ElectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(election: Election)

    @Query("SELECT * FROM elections")
    fun getAllElections(): LiveData<List<Election>>

    @Query("DELETE FROM elections where Date(electionDay) < Date(:date)")
    suspend fun deletePastElections(date: Date = Date.from(Instant.now()))

    @Query("""
        DELETE FROM saved_elections
        WHERE electionId IN (
            SELECT id AS electionId FROM elections
            WHERE Date(electionDay) < Date(:date)
        )
    """)
    suspend fun deletePastSavedElections(date: Date = Date.from(Instant.now()))

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveElection(savedElection: SavedElection)

    suspend fun saveElection(election: Election) = saveElection(SavedElection(election.id))

    @Query("SELECT * FROM elections WHERE id = :id AND id IN saved_elections")
    suspend fun getSavedElection(id: Int): Election?

    @Query("DELETE FROM saved_elections where electionId = :id")
    suspend fun deleteSavedElection(id: Int)

    suspend fun deleteSavedElection(election: Election) = deleteSavedElection(election.id)

    @Query("SELECT * FROM elections WHERE id IN saved_elections")
    fun getAllSavedElections(): LiveData<List<Election>>
}