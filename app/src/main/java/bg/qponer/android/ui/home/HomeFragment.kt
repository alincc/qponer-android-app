package bg.qponer.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private val voucherAdapter = VoucherAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LoginViewModel.AuthenticationState.Unauthenticated -> navigateToLogin()
                is LoginViewModel.AuthenticationState.Authenticated -> homeViewModel.userId =
                    it.user.userId
                else -> { /* do nothing */
                }
            }
        })

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        voucherList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = voucherAdapter
        }

        homeViewModel.vouchers.observe(viewLifecycleOwner, Observer {
            voucherAdapter.setData(it)
        })
    }

    private fun navigateToLogin() = findNavController().navigate(R.id.navigation_login)

}