package com.miwfem.socialtourismnetwork.ui.auth

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.data.viewmodel.AuthViewModel
import com.miwfem.socialtourismnetwork.databinding.FragmentAuthBinding
import com.miwfem.socialtourismnetwork.ui.base.BaseFragment
import com.miwfem.socialtourismnetwork.ui.main.MainActivity
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
                if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                    if (authViewModel.singUp(
                            emailEditText.text.toString(),
                            passwordEditText.text.toString()
                        )
                    ) {
                        (requireActivity() as? MainActivity)?.onBackPressed()
                    } else {
                        showAlert(
                            title = getString(R.string.error),
                            message = getString(R.string.sing_up_error),
                            positiveButton = getString(R.string.accept)
                        )
                    }
                } else {
                    showAlert(
                        message = getString(R.string.fill_error),
                        positiveButton = getString(R.string.accept)
                    )
                }
            }
            loginButton.setOnClickListener {
                if (emailEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                    if (authViewModel.login(
                            emailEditText.text.toString(),
                            passwordEditText.text.toString()
                        )
                    ) {
                        (requireActivity() as? MainActivity)?.onBackPressed()
                    } else {
                        showAlert(
                            title = getString(R.string.error),
                            message = getString(R.string.login_error),
                            positiveButton = getString(R.string.accept)
                        )
                    }
                } else {
                    showAlert(
                        message = getString(R.string.fill_error),
                        positiveButton = getString(R.string.accept)
                    )
                }
            }
        }
    }

    private fun showAlert(title: String = "", message: String = "", positiveButton: String = "") {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveButton, null)
        val dialog = builder.create()
        dialog.show()
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