package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.*
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.representatives
import com.example.android.politicalpreparedness.representative.model.Representative
import com.example.android.politicalpreparedness.util.TransformationsUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepresentativeViewModel : ViewModel() {
    private val _address = MutableLiveData<Address>()
    private val _representatives = MutableLiveData<List<Representative>>()

    val address: LiveData<Address> = _address
    val representatives: LiveData<List<Representative>> = _representatives

    val hasRepresentativesData = TransformationsUtils.map(representatives, false) { it != null }

    fun fetchRepresentatives(address: Address) = viewModelScope.launch(Dispatchers.IO) {
        _address.postValue(address)

        try {
            val response = CivicsApi.retrofitService.getRepresentatives(address.toFormattedString())

            if (!response.isSuccessful) {
                return@launch
            }

            _representatives.postValue(response.body()!!.representatives)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
