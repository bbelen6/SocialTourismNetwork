package com.miwfem.socialtourismnetwork.presentation.ui.addPost.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetLocationsUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.SavePostUseCase
import com.miwfem.socialtourismnetwork.presentation.mapper.map
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.LocationVO
import com.miwfem.socialtourismnetwork.presentation.ui.addPost.model.PostVO
import com.miwfem.socialtourismnetwork.utils.ResultType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddPostViewModel(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val savePostUseCase: SavePostUseCase
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
            _locations.value = getLocationsUseCase.execute().data?.map()
        }
    }

    fun savePost(post: PostVO): ResultType {
        return savePostUseCase.execute(
            SavePostUseCase.Params(post.map())
        )
    }

    fun getLocationByPosition(position: Int): LocationVO? {
        return locations.value?.get(position)
    }

}