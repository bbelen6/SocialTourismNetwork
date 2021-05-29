package com.miwfem.socialtourismnetwork.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.miwfem.socialtourismnetwork.ui.main.MainActivity

abstract class BaseFragment(@LayoutRes private val layout: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout, container, false)
        setUpDataBinding(view)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleExtras()
    }

    abstract fun setUpDataBinding(view: View)

    open fun handleOnBackPressed(): Boolean = false

    protected abstract fun getBundleExtras()

    fun bottomMenuVisibility(isVisible: Boolean) {
        (requireActivity() as? MainActivity)?.let { activity ->
            if (isVisible) activity.showBottomMenu()
            else activity.hideBottomMenu()
        }
    }
}