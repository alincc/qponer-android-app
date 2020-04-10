package bg.qponer.android.ui.businesses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import bg.qponer.android.QponerApp
import bg.qponer.android.R
import kotlinx.android.synthetic.main.fragment_businesses.*

class BusinessesFragment : Fragment() {

    private val homeViewModel by activityViewModels<BusinessesViewModel> {
        (requireContext().applicationContext as QponerApp).viewModelFactory
    }

    private val businessOwnerAdapter = BusinessOwnerAdapter()
        .apply {
        onItemClickListener = { business -> Toast.makeText(requireContext(),"Business clicked: ${business.businessName}", Toast.LENGTH_SHORT).show() }
        onItemLeaderButtonClickListener = { business -> Toast.makeText(requireContext(),"Business view leaders clicked: ${business.businessName}", Toast.LENGTH_SHORT).show() }
        onItemBuyVoucherClickListener = { business -> Toast.makeText(requireContext(),"Business buy voucher clicked: ${business.businessName}", Toast.LENGTH_SHORT).show() }
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
        return inflater.inflate(R.layout.fragment_businesses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        businessOwnerList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = businessOwnerAdapter
        }

        homeViewModel.businessOwners.observe(viewLifecycleOwner, Observer {
            businessOwnerAdapter.setData(it)
        })
    }

    private fun loadOwners() {
        homeViewModel.filter = ""
    }


}
