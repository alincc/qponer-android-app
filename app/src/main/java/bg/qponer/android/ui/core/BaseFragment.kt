package bg.qponer.android.ui.core

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

abstract class BaseFragment<VDB : ViewDataBinding, VM : ViewModel> : Fragment() {

    @get:LayoutRes
    protected abstract val layoutResId: Int
    protected abstract val viewModel: VM

    protected lateinit var dataBinding: VDB

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
}