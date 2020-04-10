package bg.qponer.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import bg.qponer.android.QponerApp
import bg.qponer.android.R
import bg.qponer.android.ui.login.LoginViewModel

class HomeFragment : Fragment() {

    private val loginViewModel by activityViewModels<LoginViewModel> {
        (requireContext().applicationContext as QponerApp).viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LoginViewModel.AuthenticationState.Unauthenticated -> navigateToLogin()
                is LoginViewModel.AuthenticationState.Authenticated -> loadHome()
                else -> { /* do nothing */
                }
            }
        })

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun navigateToLogin() = findNavController().navigate(R.id.navigation_login)

    private fun loadHome(): Unit {
        // Implement me
    }

}