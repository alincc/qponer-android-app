package bg.qponer.android.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import bg.qponer.android.QponerApp
import bg.qponer.android.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val loginViewModel by activityViewModels<LoginViewModel> {
        (requireContext().applicationContext as QponerApp).viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback { /* do nothing */ }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loginButton.setOnClickListener {
            loginViewModel.login(
                usernameField.text.toString(),
                passwordField.text.toString()
            )
        }

        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer {
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