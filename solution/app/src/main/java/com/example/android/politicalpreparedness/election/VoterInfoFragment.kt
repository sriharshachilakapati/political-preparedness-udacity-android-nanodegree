package com.example.android.politicalpreparedness.election

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
    private val viewModel by viewModels<VoterInfoViewModel>(factoryProducer = {
        VoterInfoViewModelFactory(requireContext(), args.election)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar!!.title = args.election.name
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentVoterInfoBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.election = args.election
        binding.viewModel = viewModel

        //TODO: Populate voter info -- hide views without provided data.
        /**
        Hint: You will need to ensure proper data is provided from previous fragment.
         */


        //TODO: Handle loading of URLs

        //TODO: Handle save button UI state
        //TODO: cont'd Handle save button clicks

        return binding.root
    }

    //TODO: Create method to load URL intents

}