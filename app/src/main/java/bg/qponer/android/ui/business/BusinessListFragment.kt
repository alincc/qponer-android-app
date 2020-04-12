package bg.qponer.android.ui.business

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bg.qponer.android.R
import bg.qponer.android.ui.activityViewModelsUsingAppFactory
import kotlinx.android.synthetic.main.fragment_business_list.*

class BusinessListFragment : Fragment() {

    private val businessViewModel by activityViewModelsUsingAppFactory<BusinessSharedViewModel>()

    private val businessOwnerAdapter = BusinessOwnerAdapter()
        .apply {
            onItemClickListener = { business ->
                businessViewModel.select(business)
                findNavController().navigate(R.id.navigation_business_details)
            }
            onItemLeaderButtonClickListener = { business ->
                Toast.makeText(
                    requireContext(),
                    "Business view leaders clicked: ${business.businessName}",
                    Toast.LENGTH_SHORT
                ).show()
            }
            onItemBuyVoucherClickListener = { business ->
                Toast.makeText(
                    requireContext(),
                    "Business buy voucher clicked: ${business.businessName}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadOwners()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_business_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        businessOwnerList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = businessOwnerAdapter
        }

        businessViewModel.businessOwners.observe(viewLifecycleOwner, Observer {
            businessOwnerAdapter.setData(it)
        })
    }

    private fun loadOwners() {
        businessViewModel.filter = ""
    }


}
