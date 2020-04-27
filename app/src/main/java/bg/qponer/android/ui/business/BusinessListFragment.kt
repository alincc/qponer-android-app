package bg.qponer.android.ui.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bg.qponer.android.R
import bg.qponer.android.databinding.FragmentBusinessListBinding
import bg.qponer.android.ui.activityViewModelsUsingAppFactory
import bg.qponer.android.ui.core.BaseFragment

class BusinessListFragment : BaseFragment<FragmentBusinessListBinding, BusinessSharedViewModel>() {

    override val layoutResId: Int = R.layout.fragment_business_list

    override val viewModel by activityViewModelsUsingAppFactory<BusinessSharedViewModel>()

    private val businessOwnerAdapter = BusinessOwnerAdapter()
        .apply {
            onItemClickListener = { business ->
                viewModel.select(business)
                findNavController().navigate(R.id.navigation_business_details)
            }
            onItemLeaderButtonClickListener = { business ->
                Toast.makeText(
                    requireContext(),
                    "Business view leaders clicked: ${business.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            onItemBuyVoucherClickListener = { business ->
                Toast.makeText(
                    requireContext(),
                    "Business buy voucher clicked: ${business.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadOwners()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dataBinding.businessOwnerList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = businessOwnerAdapter
        }

        viewModel.businessOwners.observe(viewLifecycleOwner, Observer {
            businessOwnerAdapter.setData(it)
        })
    }

    override fun onCreateAppBarView(inflater: LayoutInflater, container: ViewGroup?): View? =
        inflater.inflate(R.layout.toolbar_business_list, container, false)
    
    private fun loadOwners() {
        viewModel.filter = ""
    }


}
