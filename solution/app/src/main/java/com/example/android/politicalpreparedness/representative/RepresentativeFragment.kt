package com.example.android.politicalpreparedness.representative

import android.content.Context
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.databinding.FragmentRepresentativeBinding
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.adapter.RepresentativeListAdapter
import java.util.*

class RepresentativeFragment : Fragment() {
    private val viewModel by viewModels<RepresentativeViewModel>()

    private lateinit var binding: FragmentRepresentativeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRepresentativeBinding.inflate(inflater, container, false).apply {
            viewModel = this@RepresentativeFragment.viewModel
            lifecycleOwner = this@RepresentativeFragment
        }

        populateStatesInformation()
        populateAddressInformation()

        binding.recyclerViewRepresentatives.adapter = RepresentativeListAdapter().apply {
            viewModel.representatives.observe(viewLifecycleOwner) { submitList(it) }
        }

        binding.findRepresentativesButton.setOnClickListener { findMyRepresentatives() }
        binding.useLocationButton.setOnClickListener { findWithLocation() }

        return binding.root
    }

    private fun findMyRepresentatives() {
        val address = with(binding) {
            Address(
                    addressLine1.text.toString(),
                    addressLine2.text.toString(),
                    addressCity.text.toString(),
                    addressState.text.toString(),
                    addressZip.text.toString()
            )
        }

        viewModel.fetchRepresentatives(address)
    }

    private fun findWithLocation() {
        // TODO:
    }

    private fun populateStatesInformation() {
        val statesArray = requireContext().resources.getStringArray(R.array.states)
        binding.addressState.setAdapter(ArrayAdapter(requireContext(), R.layout.layout_state_list_item, statesArray))
    }

    private fun populateAddressInformation() = viewModel.address.observe(viewLifecycleOwner) {
        with(binding) {
            addressLine1.setText(it.line1)
            addressLine2.setText(it.line2)
            addressCity.setText(it.city)
            addressState.setText(it.state)
            addressZip.setText(it.zip)
        }
    }

    private fun checkLocationPermissions(): Boolean {
        return isPermissionGranted()
    }

    private fun isPermissionGranted(): Boolean {
        //TODO: Check if permission is already granted and return (true = granted, false = denied/other)
        return false
    }

    private fun getLocation() {
        //TODO: Get location from LocationServices
        //TODO: The geoCodeLocation method is a helper function to change the lat/long location to a human readable street address
    }

    private fun geoCodeLocation(location: Location): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        return geocoder.getFromLocation(location.latitude, location.longitude, 1)
                .map { address ->
                    Address(address.thoroughfare, address.subThoroughfare, address.locality, address.adminArea, address.postalCode)
                }
                .first()
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }
}