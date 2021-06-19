package com.miwfem.socialtourismnetwork.presentation.ui.messages.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentMessagesBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.common.model.MessageVO
import com.miwfem.socialtourismnetwork.presentation.ui.messages.adapter.MessageAdapter
import com.miwfem.socialtourismnetwork.presentation.ui.messages.interfaces.ItemMessageListener
import com.miwfem.socialtourismnetwork.presentation.ui.messages.viewmodel.MessageViewModel
import com.miwfem.socialtourismnetwork.utils.ResultType
import com.miwfem.socialtourismnetwork.utils.USER
import org.koin.androidx.viewmodel.ext.android.viewModel

class MessagesFragment : BaseFragment(R.layout.fragment_messages), ItemMessageListener {

    private lateinit var messagesBinding: FragmentMessagesBinding
    private val messageViewModel: MessageViewModel by viewModel()
    private var logUser: String? = null
    private var messageAdapter: MessageAdapter? = null

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
                messagesBinding.noMessages.isVisible = it.isEmpty()
                setMessagesAdapter(it)
            })
        }
    }

    private fun setMessagesAdapter(messages: List<MessageVO>) {
        if (messageAdapter == null) messageAdapter = MessageAdapter(this)
        with(messagesBinding) {
            if (rvMessages.adapter == null) rvMessages.adapter = messageAdapter
            messageAdapter?.addDataSet(messages)
        }
    }

    companion object {
        fun newInstance(logUser: String?) = MessagesFragment().apply {
            arguments = Bundle().apply {
                putString(USER, logUser)
            }
        }
    }

    override fun deleteMessage(message: MessageVO) {
        showDeleteMessageAlert(message)
    }

    private fun showDeleteMessageAlert(message: MessageVO) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.delete_message))
            .setMessage(getString(R.string.delete_message_alert))
            .setPositiveButton(getString(R.string.accept)) { _, _ ->
                messageViewModel.deleteMessage(message).apply {
                    when (this) {
                        ResultType.SUCCESS -> showToast(getString(R.string.delete_message_success))
                        ResultType.ERROR -> showToast(getString(R.string.delete_message_error))
                    }
                }
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    override fun seeDetailsMessage(message: MessageVO) {
        showSeeAllPostDialog(message = message)
    }
}