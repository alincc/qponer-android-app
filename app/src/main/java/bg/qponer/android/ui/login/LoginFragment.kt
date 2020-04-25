package bg.qponer.android.ui.login

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import bg.qponer.android.R
import bg.qponer.android.databinding.FragmentLoginBinding
import bg.qponer.android.ui.activityViewModelsUsingAppFactory
import bg.qponer.android.ui.core.BaseFragment
import com.google.android.material.snackbar.Snackbar

class LoginFragment : BaseFragment<FragmentLoginBinding, LoginViewModel>() {

    override val layoutResId = R.layout.fragment_login

    override val viewModel by activityViewModelsUsingAppFactory<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback { /* do nothing */ }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer {
            when (it) {
                is LoginViewModel.AuthenticationState.Unauthenticated -> { /* do nothing */
                }
                is LoginViewModel.AuthenticationState.Authenticated -> onAuthenticated()
                is LoginViewModel.AuthenticationState.InvalidAuthentication -> onInvalidAuthentication()
            }
        })
    }

    private fun onAuthenticated() = findNavController().popBackStack()

    private fun onInvalidAuthentication() = Snackbar.make(
        requireView(),
        "Could not login. Please, try again later",
        Snackbar.LENGTH_SHORT
    ).show()

}