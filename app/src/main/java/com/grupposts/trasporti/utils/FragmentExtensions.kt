package com.grupposts.trasporti.utils

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.ToolbarCommonBinding
import com.grupposts.trasporti.features.MainActivity
import timber.log.Timber

//fun Fragment.showLoading(show: Boolean) {
//    if (show)
//        findNavController().navigate(R.id.loadingDialog)
//    else
//        findNavController().navigateUp()
//}

fun Fragment.showError(e: Exception, title: String? = getString(R.string.attention)) {
    findNavController().navigate(
        R.id.customAlertDialog,
        bundleOf("title" to title, "message" to e.message)
    )
}

fun Fragment.showToolbar(
    title: String? = null,
    subtitle: String? = null,
    onBackPressed: (() -> Unit)? = null
) {
    (requireActivity() as? MainActivity)?.let { activity ->
        ToolbarCommonBinding.bind(activity.findViewById(R.id.toolbar)).apply {
            toolbarTitle.text = title
            toolbarSubtitle.text = subtitle
            toolbarSubtitle.visibility = if (subtitle != null) View.VISIBLE else View.GONE
            toolbarBackButton.setOnClickListener {
                onBackPressed?.invoke() ?: findNavController().navigateUp()
            }
            toolbarSearchButton.visibility = View.GONE
            root.visibility = View.VISIBLE
        }
    }
}

fun Fragment.showToolbarWithSearch(
    title: String? = null,
    subtitle: String? = null,
    onSearchSubmit: ((String) -> Unit)? = null,
    onSearchClear: (() -> Unit)? = null
) {
    (requireActivity() as? MainActivity)?.let { activity ->
        ToolbarCommonBinding.bind(activity.findViewById(R.id.toolbar)).apply {
            toolbarTitle.text = title
            toolbarSubtitle.text = subtitle
            toolbarSubtitle.visibility = if (subtitle != null) View.VISIBLE else View.GONE
            toolbarBackButton.setOnClickListener {
                findNavController().navigateUp()
            }

            toolbarSearchButton.setOnSearchClickListener {
                toolbarTitle.visibility = View.GONE
                toolbarBackButton.visibility = View.GONE
                toolbarSubtitle.visibility = View.GONE
            }

            toolbarSearchButton.setOnCloseListener {
                toolbarTitle.visibility = View.VISIBLE
                toolbarBackButton.visibility = View.VISIBLE
                toolbarSubtitle.visibility = View.VISIBLE
                false
            }

            toolbarSearchButton.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    onSearchSubmit?.invoke(query ?: "")
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrEmpty()) {
                        onSearchClear?.invoke()
                    }
                    return true
                }
            })

            toolbarSearchButton.visibility = View.VISIBLE
            root.visibility = View.VISIBLE
        }
    }
}

fun Fragment.hideToolbar() {
    (requireActivity() as? MainActivity)?.let {
        ToolbarCommonBinding.bind(it.findViewById(R.id.toolbar)).apply {
            root.visibility = View.GONE
        }
    }
}