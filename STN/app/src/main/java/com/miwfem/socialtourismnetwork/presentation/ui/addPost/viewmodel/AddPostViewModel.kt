package com.miwfem.socialtourismnetwork.presentation.ui.addPost.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetLocationsUseCase
import com.miwfem.socialtourismnetwork.presentation.mapper.map
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.LocationVO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPostViewModel(
    private val getGetLocationsUseCase: GetLocationsUseCase
) : ViewModel() {

    private val _locations by lazy { MutableLiveData<List<LocationVO>>() }
    val locations: LiveData<List<LocationVO>>
        get() = _locations

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getLocations()
        }
    }

    private fun getLocations() {
        viewModelScope.launch {
            _locations.value = getGetLocationsUseCase.execute().data?.map()
        }
    }

}