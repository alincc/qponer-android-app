package bg.qponer.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bg.qponer.android.QponerApp
import bg.qponer.android.R
import bg.qponer.android.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private val loginViewModel by activityViewModels<LoginViewModel> {
        (requireContext().applicationContext as QponerApp).viewModelFactory
    }

    private val homeViewModel by activityViewModels<HomeViewModel> {
        (requireContext().applicationContext as QponerApp).viewModelFactory
    }

    private val businessOwnerAdapter = BusinessOwnerAdapter().apply {
        onItemClickListener = { business -> Toast.makeText(requireContext(),"Business clicked: ${business.businessName}", Toast.LENGTH_SHORT).show() }
        onItemLeaderButtonClickListener = { business -> Toast.makeText(requireContext(),"Business view leaders clicked: ${business.businessName}", Toast.LENGTH_SHORT).show() }
        onItemBuyVoucherClickListener = { business -> Toast.makeText(requireContext(),"Business buy voucher clicked: ${business.businessName}", Toast.LENGTH_SHORT).show() }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LoginViewModel.AuthenticationState.Unauthenticated -> navigateToLogin()
                is LoginViewModel.AuthenticationState.Authenticated -> loadOwners()
                else -> { /* do nothing */
                }
            }
        })

        return root
    }

    private fun loadOwners() {
        homeViewModel.filter = ""
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        businessOwnerList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = businessOwnerAdapter
        }

        homeViewModel.businessOwners.observe(viewLifecycleOwner, Observer {
            businessOwnerAdapter.setData(it)
        })
    }

    private fun navigateToLogin() = findNavController().navigate(R.id.navigation_login)
}
