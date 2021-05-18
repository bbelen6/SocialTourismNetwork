package com.miwfem.socialtourismnetwork.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.utils.EMAIL
import com.miwfem.socialtourismnetwork.utils.PROVIDER

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