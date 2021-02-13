package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import java.time.Instant
import java.util.*

class ElectionsFragment : Fragment() {
    private lateinit var binding: FragmentElectionBinding
    //TODO: Declare ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentElectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        //TODO: Add ViewModel values and create ViewModel

        binding.run {
            configureRecyclerView(recyclerViewUpcomingElections)
            configureRecyclerView(recyclerViewSavedElections)

            //TODO: Populate recycler adapters
        }

        return binding.root
    }

    private fun configureRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = ElectionListAdapter(this::onElectionClicked).apply {
            val list = mutableListOf<Election>()

            repeat(10) {
                val division = Division("DIV $it", "US", "Florida")
                list += Election(it, "Election $it", Date.from(Instant.now()), division)
            }

            submitList(list)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onElectionClicked(election: Election) {
        val directions = ElectionsFragmentDirections.toVoterInfoFragment(election)
        findNavController().navigate(directions)
    }

    //TODO: Refresh adapters when fragment loads
}