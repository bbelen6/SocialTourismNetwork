package com.miwfem.socialtourismnetwork.presentation.ui.info.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetTiaLocationsNearestDateUseCase
import com.miwfem.socialtourismnetwork.presentation.common.model.TiaLocationVO
import com.miwfem.socialtourismnetwork.presentation.mapper.map
import kotlinx.coroutines.launch

class InfoViewModel(
    private val getTiaLocationsNearestDateUseCase: GetTiaLocationsNearestDateUseCase
) : ViewModel() {

    private val _tiaLocations by lazy { MutableLiveData<List<TiaLocationVO>>() }
    val tiaLocation: LiveData<List<TiaLocationVO>>
        get() = _tiaLocations

    init {
        getTiaLocations()
    }

    fun getTiaLocations() {
        viewModelScope.launch {
            _tiaLocations.value = getTiaLocationsNearestDateUseCase.execute().data?.map()
        }
    }
}