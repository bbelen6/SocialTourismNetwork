package com.miwfem.socialtourismnetwork.presentation.ui.info.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.miwfem.socialtourismnetwork.databinding.ItemInfoBinding
import com.miwfem.socialtourismnetwork.presentation.common.interfaces.AddDataSetAdapterLister
import com.miwfem.socialtourismnetwork.presentation.common.model.TiaLocationVO

class InfoAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    AddDataSetAdapterLister<TiaLocationVO> {

    private val dataSet = mutableListOf<TiaLocationVO>()

    override fun addDataSet(items: List<TiaLocationVO>) {
        dataSet.clear()
        if (items.isNotEmpty()) dataSet.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return InfoViewHolder(
            ItemInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is InfoViewHolder) holder.bindData(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    inner class InfoViewHolder(private val binding: ItemInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(info: TiaLocationVO) {
            with(binding) {
                infoLocation.text = info.location
                infoDatos.text = info.accumulatedRate.toString()
            }
        }
    }
}