package com.miwfem.socialtourismnetwork.presentation.ui.messages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miwfem.socialtourismnetwork.databinding.ItemCommunicationBinding
import com.miwfem.socialtourismnetwork.presentation.common.interfaces.AddDataSetAdapterLister
import com.miwfem.socialtourismnetwork.presentation.common.model.MessageVO

class MessageAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    AddDataSetAdapterLister<MessageVO> {

    private val dataSet = mutableListOf<MessageVO>()

    override fun addDataSet(items: List<MessageVO>) {
        dataSet.clear()
        if (items.isNotEmpty()) dataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MessageViewHolder(
            ItemCommunicationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MessageViewHolder)
            holder.bindData(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    inner class MessageViewHolder(private val binding: ItemCommunicationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(messageVO: MessageVO) {
            with(binding) {
                communicationMenssage.text = messageVO.message
            }
        }
    }

}