package com.miwfem.socialtourismnetwork.presentation.ui.messages.interfaces

import com.miwfem.socialtourismnetwork.presentation.common.model.MessageVO

interface ItemMessageListener {

    fun deleteMessage(message: MessageVO)
    fun seeDetailsMessage(message: MessageVO)

}