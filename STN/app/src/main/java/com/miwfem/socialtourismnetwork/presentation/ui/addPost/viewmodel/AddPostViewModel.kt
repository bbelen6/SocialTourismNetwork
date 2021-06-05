package com.miwfem.socialtourismnetwork.presentation.ui.addPost.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miwfem.socialtourismnetwork.businesslogic.usecase.LocationsUseCase
import com.miwfem.socialtourismnetwork.presentation.mapper.map
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.LocationVO
import kotlinx.coroutines.launch

class AddPostViewModel(
    private val getLocationsUseCase: LocationsUseCase
) : ViewModel() {

    private val _locations by lazy { MutableLiveData<List<LocationVO>>() }
    val locations: LiveData<List<LocationVO>>
        get() = _locations

    fun getLocations() {
        viewModelScope.launch {
            _locations.value = getLocationsUseCase.execute().data?.map()
        }
    }

}