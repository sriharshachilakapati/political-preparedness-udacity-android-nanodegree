package com.example.android.politicalpreparedness.election

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.politicalpreparedness.databinding.FragmentElectionBinding
import com.example.android.politicalpreparedness.election.adapter.ElectionListAdapter
import com.example.android.politicalpreparedness.network.models.Election

class ElectionsFragment : Fragment() {
    private val viewModel by activityViewModels<ElectionsViewModel>(factoryProducer = {
        ElectionsViewModelFactory(requireContext())
    })

    private lateinit var binding: FragmentElectionBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentElectionBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        binding.run {
            configureRecyclerView(recyclerViewUpcomingElections, viewModel.allElections)
            configureRecyclerView(recyclerViewSavedElections, viewModel.savedElections)
        }

        return binding.root
    }

    private fun configureRecyclerView(recyclerView: RecyclerView, dataSource: LiveData<List<Election>>) {
        recyclerView.adapter = ElectionListAdapter(this::onElectionClicked).apply {
            dataSource.observe(viewLifecycleOwner, this::submitList)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun onElectionClicked(election: Election) {
        val directions = ElectionsFragmentDirections.toVoterInfoFragment(election)
        findNavController().navigate(directions)
    }

    //TODO: Refresh adapters when fragment loads
}