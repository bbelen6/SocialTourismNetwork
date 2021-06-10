package com.miwfem.socialtourismnetwork.presentation.ui.profile.viewmodel

import androidx.lifecycle.ViewModel
import com.miwfem.socialtourismnetwork.businesslogic.model.UserEntity
import com.miwfem.socialtourismnetwork.businesslogic.usecase.SaveUserProfileUseCase
import com.miwfem.socialtourismnetwork.utils.ResultType

class ProfileViewModel(
    private val saveUserProfileUseCase: SaveUserProfileUseCase
) : ViewModel() {

    fun saveName(user: String, userName: String): ResultType {
        return saveUserProfileUseCase.execute(
            SaveUserProfileUseCase.Params(
                UserEntity(
                    user,
                    userName
                )
            )
        )
    }
}