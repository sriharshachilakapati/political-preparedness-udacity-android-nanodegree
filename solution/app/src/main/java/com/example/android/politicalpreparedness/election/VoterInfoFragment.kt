package com.example.android.politicalpreparedness.election

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.android.politicalpreparedness.databinding.FragmentVoterInfoBinding

class VoterInfoFragment : Fragment() {
    private lateinit var binding: FragmentVoterInfoBinding

    private val args by navArgs<VoterInfoFragmentArgs>()
    private val viewModel by viewModels<VoterInfoViewModel> {
        voterInfoViewModelFactory(requireContext(), args.election)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar!!.title = args.election.name
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentVoterInfoBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@VoterInfoFragment
            election = args.election
            viewModel = this@VoterInfoFragment.viewModel
        }

        viewModel.votingLocationFinderURL.observe(viewLifecycleOwner) { url ->
            binding.linkVotingLocations.setOnClickListener { openURL(url) }
        }

        viewModel.ballotInformationURL.observe(viewLifecycleOwner) { url ->
            binding.linkBallotInformation.setOnClickListener { openURL(url) }
        }

        return binding.root
    }

    private fun openURL(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url ?: return)
        startActivity(intent)
    }
}