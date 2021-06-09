package com.miwfem.socialtourismnetwork.presentation.ui.auth.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetUserNameByEmailUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.SaveUserProfileUseCase
import com.miwfem.socialtourismnetwork.presentation.common.model.UserVO
import com.miwfem.socialtourismnetwork.presentation.mapper.map
import kotlinx.coroutines.launch

class AuthViewModel(
    private val saveUserProfileUseCase: SaveUserProfileUseCase,
    private val getUserNameByEmailUseCase: GetUserNameByEmailUseCase
) : ViewModel() {

    private val _userName by lazy { MutableLiveData<String>() }
    val userName: LiveData<String>
        get() = _userName

    fun singUp(email: String, password: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
    }

    fun login(email: String, password: String): Task<AuthResult> {
        return FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
    }

    fun saveUserProfile(user: UserVO) {
        saveUserProfileUseCase.execute(SaveUserProfileUseCase.Params(user.map()))
    }

    fun getUserNameByEmail(email: String) {
        viewModelScope.launch {
            _userName.value =
                getUserNameByEmailUseCase.execute(GetUserNameByEmailUseCase.Params(email)).data
        }
    }
}