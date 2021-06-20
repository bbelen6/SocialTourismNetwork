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
import com.miwfem.socialtourismnetwork.presentation.common.model.MessageVO
import com.miwfem.socialtourismnetwork.presentation.common.model.PostVO
import com.miwfem.socialtourismnetwork.presentation.ui.main.MainActivity
import com.miwfem.socialtourismnetwork.utils.ResultType

abstract class BaseFragment(@LayoutRes private val layout: Int) : Fragment() {

    lateinit var dialog: Dialog

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

    fun showSeeAllPostDialog(
        post: PostVO? = null,
        logUser: String? = null,
        message: MessageVO? = null
    ) {
        dialog = Dialog(requireContext(), R.style.DialogTheme)
        val dialogBinding: ItemSeeAllPostBinding =
            ItemSeeAllPostBinding.inflate(LayoutInflater.from(requireContext()))
        dialogBinding.apply {
            seeAllClose.setOnClickListener {
                dialog.dismiss()
            }
            message?.let {
                seeAllComment.text = it.post
                seeAllCategory.text = getString(R.string.your_post)
                messageUser.isVisible = true
                messageUser.text = it.message
                seeAllLocation.isVisible = false
                seeAllUser.isVisible = false
                favoritePost2.isVisible = false
                seeAllContainerUserEmissary.isVisible = true
                seeAllCommunicationUserName.text = it.userEmissary
                seeAllUserMail.isVisible = !it.userEmissaryEmail.isNullOrBlank()
                seeAllUserMail.text = it.userEmissaryEmail ?: ""
            } ?: kotlin.run {
                seeAllComment.text = post?.comment
                seeAllLocation.text = post?.location
                seeAllUser.text =
                    if (post?.userName?.isNotEmpty() == true) post.userName else post?.user
                seeAllCategory.text = post?.category
                favoritePost2.isVisible = logUser != null
                if (post?.isFav == true) {
                    favoritePost2.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    favoritePost2.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
            }
        }
        dialog.show()
        dialog.setContentView(dialogBinding.root)
    }

    fun resultDelete(result: ResultType) {
        when (result) {
            ResultType.SUCCESS -> {
                showToast(getString(R.string.delete_post))
            }
            ResultType.ERROR -> {
                showToast(getString(R.string.delete_post_error))
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}