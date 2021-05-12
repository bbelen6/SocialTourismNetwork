package com.miwfem.socialtourismnetwork.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.ui.main.MainActivity
import com.miwfem.socialtourismnetwork.utils.EMAIL
import com.miwfem.socialtourismnetwork.utils.PROVIDER
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private var email: String? = null
    private var provider: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            email = it.getString(EMAIL)
            provider = it.getString(PROVIDER)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        email_text_view.text = email
        provider_text_view.text = provider

        close_session_button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            (requireActivity() as? MainActivity)?.onBackPressed()
        }
    }

    companion object {
        fun newInstance(email: String, provider: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(EMAIL, email)
                    putString(PROVIDER, provider)
                }
            }
    }
}