package com.miwfem.socialtourismnetwork.presentation.ui.auth.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentAuthBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.ui.auth.viewmodel.AuthViewModel
import com.miwfem.socialtourismnetwork.presentation.ui.main.MainActivity
import com.miwfem.socialtourismnetwork.utils.EMAIL
import com.miwfem.socialtourismnetwork.utils.PREFERENCES_FILE
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : BaseFragment(R.layout.fragment_auth) {

    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var authBinding: FragmentAuthBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomMenuVisibility(false)
    }

    override fun setUpDataBinding(view: View) {
        authBinding = FragmentAuthBinding.bind(view)
        setUpView()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferences = requireActivity().getSharedPreferences(
            PREFERENCES_FILE,
            Context.MODE_PRIVATE
        )
    }

    private fun setUpView() {
        with(authBinding) {
            authClose.setOnClickListener {
                (requireActivity() as? MainActivity)?.onBackPressed()
            }
            singUpButton.setOnClickListener {
                if (loginCorreoField.text?.isNotEmpty() == true && loginCodeField.text?.isNotEmpty() == true) {
                    authViewModel.singUp(
                        loginCorreoField.text.toString(),
                        loginCodeField.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            authSuccess(loginCorreoField.text.toString())
                        } else {
                            showAlert(
                                title = getString(R.string.error),
                                message = getString(R.string.sing_up_error),
                                positiveButton = getString(R.string.accept)
                            )
                        }
                    }
                } else {
                    showAlert(
                        message = getString(R.string.fill_error),
                        positiveButton = getString(R.string.accept)
                    )
                }
            }
            loginButton.setOnClickListener {
                if (loginCorreoField.text?.isNotEmpty() == true && loginCodeField.text?.isNotEmpty() == true) {
                    authViewModel.login(
                        loginCorreoField.text.toString(),
                        loginCodeField.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            authSuccess(loginCorreoField.text.toString())
                        } else {
                            showAlert(
                                title = getString(R.string.error),
                                message = getString(R.string.login_error),
                                positiveButton = getString(R.string.accept)
                            )
                        }
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

    override fun getBundleExtras() {
        //TODO("Not yet implemented")
    }

    override fun observeViewModel() {
        //TODO("Not yet implemented")
    }

    private fun authSuccess(email: String) {
        (requireActivity() as? MainActivity)?.let { activity ->
            activity.onBackPressed()
            activity.changeTopIcon(true)
            val sharedEdit = sharedPreferences.edit()
            sharedEdit?.putString(EMAIL, email)
            sharedEdit.apply()
            activity.setBottomOptions(email)
        }
    }

    companion object {
        fun newInstance() = AuthFragment()
    }
}