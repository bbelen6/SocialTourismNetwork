package com.miwfem.socialtourismnetwork.presentation.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.miwfem.socialtourismnetwork.R
import com.miwfem.socialtourismnetwork.databinding.ItemSeeAllPostBinding
import com.miwfem.socialtourismnetwork.presentation.common.model.PostVO
import com.miwfem.socialtourismnetwork.presentation.ui.main.MainActivity

abstract class BaseFragment(@LayoutRes private val layout: Int) : Fragment() {

    private lateinit var dialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(layout, container, false)
        setUpDataBinding(view)
        observeViewModel()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleExtras()
    }

    abstract fun setUpDataBinding(view: View)

    open fun handleOnBackPressed(): Boolean = false

    protected abstract fun getBundleExtras()

    protected abstract fun observeViewModel()

    fun bottomMenuVisibility(isVisible: Boolean) {
        (requireActivity() as? MainActivity)?.let { activity ->
            if (isVisible) activity.showBottomMenu()
            else activity.hideBottomMenu()
        }
    }

    fun showSeeAllPostDialog(post: PostVO, logUser: String?) {
        dialog = Dialog(requireContext(), R.style.DialogTheme)
        val dialogBinding: ItemSeeAllPostBinding =
            ItemSeeAllPostBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBinding.apply {
            seeAllComment.text = post.comment
            seeAllLocation.text = post.location
            seeAllUser.text = if (post.userName.isNotEmpty()) post.userName else post.user
            seeAllCategory.text = post.category
            deletePost2.isVisible = false
            favoritePost2.isVisible = logUser != null

            if (post.isFav) {
                favoritePost2.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                favoritePost2.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }
            seeAllClose.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
        dialog.setContentView(dialogBinding.root)
    }

    fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}