package bg.qponer.android.ui.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import bg.qponer.android.BR
import java.lang.IllegalStateException

abstract class BaseFragment<VDB : ViewDataBinding, VM : ViewModel> : Fragment() {

    @get:LayoutRes
    protected abstract val layoutResId: Int
    protected abstract val viewModel: VM

    protected lateinit var dataBinding: VDB

    private lateinit var toolbarHost: ToolbarHost

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is ToolbarHost) {
            toolbarHost = context
        } else {
            throw IllegalStateException("Fragment must be attached to a ToolbarHost")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        DataBindingUtil.inflate<VDB>(inflater, layoutResId, container, false)
            .apply {
                setVariable(BR.viewModel, viewModel)
                lifecycleOwner = viewLifecycleOwner
            }
            .also {
                dataBinding = it
            }
            .root

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onCreateAppBarView(
            LayoutInflater.from(requireContext()),
            toolbarHost.getAppContainer()
        ).also {
            toolbarHost.attachToAppBarLayout(it)
        }
    }

    protected open fun onCreateAppBarView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View? = null
}