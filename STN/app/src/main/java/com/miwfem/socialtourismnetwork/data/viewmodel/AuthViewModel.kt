package com.miwfem.socialtourismnetwork.data.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    fun singUp(email: String, password: String): Boolean {
        var resultSingUp = false
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
            email, password
        ).addOnCompleteListener {
            resultSingUp = it.isSuccessful
        }
        return resultSingUp
    }

    fun login(email: String, password: String): Boolean {
        var resultLogin = false
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
            email, password
        ).addOnCompleteListener {
            resultLogin = it.isSuccessful
        }
        return resultLogin
    }
}