package com.miwfem.socialtourismnetwork.presentation.ui.auth.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentAuthBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.common.model.UserVO
import com.miwfem.socialtourismnetwork.presentation.ui.auth.viewmodel.AuthViewModel
import com.miwfem.socialtourismnetwork.presentation.ui.main.MainActivity
import com.miwfem.socialtourismnetwork.utils.EMAIL
import com.miwfem.socialtourismnetwork.utils.PREFERENCES_FILE
import com.miwfem.socialtourismnetwork.utils.USER_NAME
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthFragment : BaseFragment(R.layout.fragment_auth) {

    private val authViewModel: AuthViewModel by viewModel()
    private lateinit var authBinding: FragmentAuthBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var registerView = false

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
                registerView = !registerView
                if (registerView)
                    registerUserView()
                else
                    loginUserView()
            }
            loginButton.setOnClickListener {
                if (registerView)
                    checkUserName()
                else
                    loginUser()
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
        //Empty body
    }

    override fun observeViewModel() {
        with(authViewModel) {
            userName.observe(viewLifecycleOwner, {
                authSuccess(authBinding.loginCorreoField.text.toString(), it)
            })
            existUserName.observe(viewLifecycleOwner, {
                if (it) {
                    fillErrorName()
                } else {
                    registerUser()
                }
            })
        }
    }

    private fun registerUserView() {
        with(authBinding) {
            userName.isVisible = true
            loginButton.text = getString(R.string.register)
            noAccount.isVisible = false
            singUpButton.text = getString(R.string.login)
        }
    }

    private fun checkUserName() {
        with(authBinding) {
            if (loginCorreoField.text?.isNotEmpty() == true && loginCodeField.text?.isNotEmpty() == true && registerField.text?.isNotEmpty() == true) {
                authViewModel.checkUserName(authBinding.registerField.text.toString())
            } else {
                fillError(true)
            }
        }
    }

    private fun registerUser() {
        with(authBinding) {
            if (loginCorreoField.text?.isNotEmpty() == true && loginCodeField.text?.isNotEmpty() == true && registerField.text?.isNotEmpty() == true) {
                authViewModel.singUp(
                    loginCorreoField.text.toString(),
                    loginCodeField.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        authViewModel.saveUserProfile(
                            UserVO(
                                loginCorreoField.text.toString(),
                                registerField.text.toString()
                            )
                        )
                        authSuccess(loginCorreoField.text.toString(), registerField.text.toString())
                    } else {
                        showAlert(
                            title = getString(R.string.error),
                            message = getString(R.string.sing_up_error),
                            positiveButton = getString(R.string.accept)
                        )
                    }
                }
            } else {
                fillError(true)
            }
        }
    }

    private fun loginUser() {
        with(authBinding) {
            if (loginCorreoField.text?.isNotEmpty() == true && loginCodeField.text?.isNotEmpty() == true) {
                authViewModel.login(
                    loginCorreoField.text.toString(),
                    loginCodeField.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {
                        authViewModel.getUserNameByEmail(loginCorreoField.text.toString())
                    } else {
                        showAlert(
                            title = getString(R.string.error),
                            message = getString(R.string.login_error),
                            positiveButton = getString(R.string.accept)
                        )
                    }
                }
            } else {
                fillError(false)
            }
        }
    }

    private fun fillError(register: Boolean) {
        with(authBinding) {
            if (loginCorreoField.text?.isNotEmpty() == false) loginCorreoField.error =
                getString(R.string.fill_error)
            else loginCorreoField.error = null
            if (loginCodeField.text?.isNotEmpty() == false) loginCodeField.error =
                getString(R.string.fill_error)
            else loginCodeField.error = null
            if (register) {
                if (registerField.text?.isNotEmpty() == false) registerField.error =
                    getString(R.string.fill_error)
                else registerField.error = null
            }
        }
    }

    private fun fillErrorName() {
        authBinding.registerField.error = getString(R.string.error_name)
    }

    private fun loginUserView() {
        with(authBinding) {
            userName.isVisible = false
            loginButton.text = getString(R.string.login)
            noAccount.isVisible = true
            singUpButton.text = getString(R.string.register)
        }
    }

    private fun authSuccess(email: String, userName: String) {
        (requireActivity() as? MainActivity)?.let { activity ->
            activity.backAndRefreshHome(email, userName)
            activity.changeTopIcon(true)
            val sharedEdit = sharedPreferences.edit()
            sharedEdit?.putString(EMAIL, email)
            sharedEdit?.putString(USER_NAME, userName)
            sharedEdit.apply()
            activity.setBottomOptions(email)
        }
    }

    companion object {
        fun newInstance() = AuthFragment()
    }
}