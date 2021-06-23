package com.miwfem.socialtourismnetwork.presentation.ui.info

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.FragmentInfoBinding
import com.miwfem.socialtourismnetwork.presentation.base.BaseFragment
import com.miwfem.socialtourismnetwork.presentation.common.model.TiaLocationVO
import com.miwfem.socialtourismnetwork.presentation.ui.info.viewmodel.InfoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : BaseFragment(R.layout.fragment_info) {

    private lateinit var infoBinding: FragmentInfoBinding
    private val infoViewModel: InfoViewModel by viewModel()

    override fun setUpDataBinding(view: View) {
        infoBinding = FragmentInfoBinding.bind(view)
        showLoading()
    }

    override fun getBundleExtras() {
    }

    override fun observeViewModel() {
        with(infoViewModel) {
            tiaLocation.observe(viewLifecycleOwner, {
                showAlert("Los datos que se muestran a continuación son del día ${it[0].date}")
                getLocations(it)
                hideLoading()
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

    private fun getLocations(tiaLocations: List<TiaLocationVO>) {
        val locations = mutableListOf<String>()
        tiaLocations.forEach {
            if (!it.location.contains("Madrid-"))
                locations.add(it.location)
        }
        locations.add("Madrid")
        locations.distinct()
    }

    private fun showLoading() {
        infoBinding.progressBarCyclic.isVisible = true
    }

    private fun hideLoading() {
        infoBinding.progressBarCyclic.isVisible = false
    }

    companion object {
        fun newInstance() = InfoFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }

}