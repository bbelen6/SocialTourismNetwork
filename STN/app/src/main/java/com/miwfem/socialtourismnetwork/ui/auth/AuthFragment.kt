package com.miwfem.socialtourismnetwork.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.data.viewmodel.AuthViewModel
import com.miwfem.socialtourismnetwork.databinding.FragmentAuthBinding
import com.miwfem.socialtourismnetwork.ui.base.BaseFragment
import com.miwfem.socialtourismnetwork.ui.home.HomeFragment
import com.miwfem.socialtourismnetwork.ui.main.MainActivity
import com.miwfem.socialtourismnetwork.utils.TAG_HOME
import kotlinx.android.synthetic.main.fragment_auth.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : BaseFragment(R.layout.fragment_auth) {

    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var authBinding: FragmentAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //GET BUNDLE EXTRA
        bottomMenuVisibility(false)
    }

    override fun setUpDataBinding(view: View) {
        authBinding = FragmentAuthBinding.bind(view)
        setUpView()
    }

    private fun setUpView() {
        with(authBinding) {
            authClose.setOnClickListener {
                (requireActivity() as? MainActivity)?.onBackPressed()
            }
            singUpButton.setOnClickListener {
                if (emailEditText.text.isNotEmpty() && password_edit_text.text.isNotEmpty()) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", "HOME")
                        } else {
                            showAlert()
                        }
                    }
                }
            }

            loginButton.setOnClickListener {
                if (emailEditText.text.isNotEmpty() && password_edit_text.text.isNotEmpty()) {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", "Home")
                        } else {
                            showAlert()
                        }
                    }
                }
            }
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Error")
            .setMessage("Se ha producido un error en la autenticación del usuario")
            .setPositiveButton("Aceptar", null)
        val dialog = builder.create()
        dialog.show()
    }

    private fun showHome(email: String, provider: String) {
        (requireActivity() as? MainActivity)?.addFragment(
            HomeFragment.newInstance(
                email,
                provider
            ), R.id.fragmentComplete, TAG_HOME
        )
    }

    override fun handleOnBackPressed(): Boolean {
        bottomMenuVisibility(true)
        return true
    }

    companion object {
        fun newInstance() =
            AuthFragment().apply {
            }
    }
}