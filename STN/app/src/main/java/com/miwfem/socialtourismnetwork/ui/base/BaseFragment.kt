package com.miwfem.socialtourismnetwork.ui.base

import androidx.fragment.app.Fragment
import com.miwfem.socialtourismnetwork.ui.main.MainActivity

abstract class BaseFragment : Fragment() {

    open fun handleOnBackPressed(): Boolean = false

    fun bottomMenuVisibility(isVisible: Boolean) {
        (requireActivity() as? MainActivity)?.let { activity ->
            if (isVisible) activity.showBottomMenu()
            else activity.hideBottomMenu()
        }
    }
}