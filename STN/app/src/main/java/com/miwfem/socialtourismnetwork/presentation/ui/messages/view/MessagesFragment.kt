package com.miwfem.socialtourismnetwork.presentation.ui.messages.view

import android.os.Bundle
import android.util.Log
import android.view.View
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentMessagesBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.ui.messages.viewmodel.MessageViewModel
import com.miwfem.socialtourismnetwork.utils.USER
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagesFragment : BaseFragment(R.layout.fragment_messages) {

    private lateinit var messagesBinding: FragmentMessagesBinding
    private val messageViewModel: MessageViewModel by viewModel()
    private var logUser: String? = null

    override fun setUpDataBinding(view: View) {
        messagesBinding = FragmentMessagesBinding.bind(view)
    }

    override fun getBundleExtras() {
        arguments?.let {
            logUser = it.getString(USER)
        }
        messageViewModel.getMessages(logUser)
    }

    override fun observeViewModel() {
        with(messageViewModel) {
            messages.observe(viewLifecycleOwner, {
                Log.d("MESSAGES", "MESSAGES")
            })
        }
    }

    companion object {
        fun newInstance(logUser: String?) = MessagesFragment().apply {
            arguments = Bundle().apply {
                putString(USER, logUser)
            }
        }
    }
}