package com.miwfem.socialtourismnetwork.presentation.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.miwfem.socialtourismnetwork.businesslogic.usecase.SaveUserProfileUseCase
import com.miwfem.socialtourismnetwork.presentation.common.model.UserVO
import com.miwfem.socialtourismnetwork.presentation.mapper.map

class AuthViewModel(private val saveUserProfileUseCase: SaveUserProfileUseCase) : ViewModel() {

    fun singUp(email: String, password: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
    }

    fun login(email: String, password: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
    }

    fun saveUserProfile(user: UserVO) {
        saveUserProfileUseCase.execute(SaveUserProfileUseCase.Params(user.map()))
    }
}