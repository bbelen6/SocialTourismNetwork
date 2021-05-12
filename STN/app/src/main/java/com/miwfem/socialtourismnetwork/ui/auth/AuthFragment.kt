package com.miwfem.socialtourismnetwork.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.ui.home.HomeFragment
import com.miwfem.socialtourismnetwork.ui.main.MainActivity
import com.miwfem.socialtourismnetwork.utils.EMAIL
import com.miwfem.socialtourismnetwork.utils.PROVIDER
import com.miwfem.socialtourismnetwork.utils.TAG_HOME
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //GET BUNDLE EXTRA
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        sing_up_button.setOnClickListener {
            if (email_edit_text.text.isNotEmpty() && password_edit_text.text.isNotEmpty()) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    email_edit_text.text.toString(),
                    password_edit_text.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", "HOME")
                        } else {
                            showAlert()
                        }
                    }
            }
        }

        login_button.setOnClickListener {
            if (email_edit_text.text.isNotEmpty() && password_edit_text.text.isNotEmpty()) {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    email_edit_text.text.toString(),
                    password_edit_text.text.toString()
                )
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            showHome(it.result?.user?.email ?: "", "Home")
                        } else {
                            showAlert()
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
        (requireActivity() as? MainActivity)?.replaceFragment(
            HomeFragment.newInstance(
                email,
                provider
            ), R.id.fragmentComplete, TAG_HOME
        )
    }

    companion object {
        fun newInstance() =
            AuthFragment().apply {
            }
    }
}