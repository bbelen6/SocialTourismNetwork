package com.miwfem.socialtourismnetwork.presentation.ui.info.view

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentInfoBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.common.hideKeyboard
import com.miwfem.socialtourismnetwork.presentation.common.model.TiaLocationVO
import com.miwfem.socialtourismnetwork.presentation.ui.info.adapter.InfoAdapter
import com.miwfem.socialtourismnetwork.presentation.ui.info.viewmodel.InfoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : BaseFragment(R.layout.fragment_info) {

    private lateinit var infoBinding: FragmentInfoBinding
    private val infoViewModel: InfoViewModel by viewModel()
    private var infoAdapter: InfoAdapter? = null

    override fun setUpDataBinding(view: View) {
        infoBinding = FragmentInfoBinding.bind(view).apply {
            infoApplyFiltersButton.setOnClickListener {
                applyFilter(
                    infoSearchLocationFilter.selectedItem.toString(),
                    infoSearchAreaFilter.selectedItem.toString()
                )
            }
            infoFilterButton.setOnClickListener {
                showFilters(true)
            }
            infoCloseFilter.setOnClickListener {
                showFilters(false)
            }
            infoDeleteFiltersButton.setOnClickListener {
                deleteFilters()
            }
        }
        showLoading()
    }

    override fun getBundleExtras() {
    }

    override fun observeViewModel() {
        with(infoViewModel) {
            tiaLocation.observe(viewLifecycleOwner, {
                showAlert("Los datos que se muestran a continuación son del día ${it[0].date}")
                setInfoAdapter(it)
                getLocationsName()
                getAreasName()
                hideLoading()
            })
            locationsName.observe(viewLifecycleOwner, {
                val finalList = mutableListOf(getString(R.string.filter_location))
                finalList.addAll(it)
                setLocationsNameSpinner(finalList)
            })
            areasName.observe(viewLifecycleOwner, {
                val finalList = mutableListOf(getString(R.string.filter_area))
                finalList.addAll(it)
                setAreasNameSpinner(finalList)
            })
        }
    }

    private fun showAlert(message: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.attention))
            .setMessage(message)
            .setPositiveButton(getString(R.string.accept)) { dialog, _ -> dialog.dismiss() }
        dialog = builder.create()
        dialog.show()
    }

    private fun setInfoAdapter(tiaLocations: List<TiaLocationVO>) {
        if (infoAdapter == null) infoAdapter = InfoAdapter()
        with(infoBinding) {
            if (rvInfo.adapter == null) rvInfo.adapter = infoAdapter
            infoAdapter?.addDataSet(tiaLocations)
        }
    }

    private fun setLocationsNameSpinner(locations: List<String>) {
        with(infoBinding) {
            infoSearchLocationFilter.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                locations
            )
        }
    }

    private fun setAreasNameSpinner(areas: List<String>) {
        with(infoBinding) {
            infoSearchAreaFilter.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                areas
            )
        }
    }

    private fun showFilters(visibility: Boolean) {
        with(infoBinding) {
            infoFilterButton.isVisible = !visibility
            infoFilterLayout.isVisible = visibility
            if (!visibility) this@InfoFragment.hideKeyboard()
        }
    }

    private fun deleteFilters() {
        with(infoBinding) {
            infoSearchAreaFilter.setSelection(0)
            infoSearchLocationFilter.setSelection(0)
        }
        infoViewModel.tiaLocation.value?.let {
            (infoBinding.rvInfo.adapter as? InfoAdapter)?.addDataSet(
                it
            )
        }
    }

    private fun applyFilter(
        location: String?,
        area: String,
    ) {
        val finalTiaLocations = mutableListOf<TiaLocationVO>()
        infoViewModel.tiaLocation.value?.forEach { tiaLocations ->
            if ((location == tiaLocations.location) || location == getString(R.string.filter_location) &&
                (area == tiaLocations.area || area == getString(R.string.filter_area))
            ) {
                finalTiaLocations.add(tiaLocations)
            }
        }
        if (finalTiaLocations.isNotEmpty()) {
            showFilters(false)
            (infoBinding.rvInfo.adapter as? InfoAdapter)?.addDataSet(
                finalTiaLocations
            )
        } else showToast("No hay coincidencias con los filtros")
    }

    private fun showLoading() {
        infoBinding.infoLoading.isVisible = true
    }

    private fun hideLoading() {
        infoBinding.infoLoading.isVisible = false
    }

    companion object {
        fun newInstance() = InfoFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

}