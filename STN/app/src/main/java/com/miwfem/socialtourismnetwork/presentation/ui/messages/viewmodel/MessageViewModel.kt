package com.miwfem.socialtourismnetwork.presentation.ui.messages.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miwfem.socialtourismnetwork.businesslogic.usecase.DeleteMessageUseCase
import com.miwfem.socialtourismnetwork.businesslogic.usecase.GetMessagesUseCase
import com.miwfem.socialtourismnetwork.presentation.common.model.MessageVO
import com.miwfem.socialtourismnetwork.presentation.mapper.map
import com.miwfem.socialtourismnetwork.utils.ResultType
import kotlinx.coroutines.launch

class MessageViewModel(
    private val getMessagesUseCase: GetMessagesUseCase,
    private val deleteMessageUseCase: DeleteMessageUseCase
) : ViewModel() {

    private val _messages by lazy { MutableLiveData<List<MessageVO>>() }
    val messages: LiveData<List<MessageVO>>
        get() = _messages

    fun getMessages(logUser: String?) {
        viewModelScope.launch {
            _messages.value =
                getMessagesUseCase.execute(GetMessagesUseCase.Params(logUser)).data?.map()
        }
    }

    fun deleteMessage(message: MessageVO): ResultType {
        val newMessages = _messages.value?.toMutableList()
        newMessages?.remove(message)
        _messages.value = newMessages
        return deleteMessageUseCase.execute(DeleteMessageUseCase.Params(message.map()))
    }

}