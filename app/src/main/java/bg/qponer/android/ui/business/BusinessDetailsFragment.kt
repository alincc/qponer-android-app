package bg.qponer.android.ui.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import bg.qponer.android.R
import bg.qponer.android.databinding.FragmentBusinessDetailsBinding
import bg.qponer.android.ui.activityViewModelsUsingAppFactory

class BusinessDetailsFragment : Fragment() {

    private val businessViewModel by activityViewModelsUsingAppFactory<BusinessSharedViewModel>()

    private lateinit var dataBinding: FragmentBusinessDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_business_details, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        businessViewModel.selectedItem.observe(viewLifecycleOwner, Observer {
            dataBinding.apply {
                business = it
            }.also { it.executePendingBindings() }
        })
    }
}