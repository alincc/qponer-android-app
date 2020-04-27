package bg.qponer.android.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bg.qponer.android.data.model.AuthenticatedUserModel
import bg.qponer.android.data.repository.AuthRepository
import bg.qponer.android.ui.core.LiveDataTransformations
import bg.qponer.android.util.fold
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val authRepo: AuthRepository
) : ViewModel() {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isLoginButtonEnabled = LiveDataTransformations.combineLatest(
        username, password
    ) {
        val username = it[0] as String?
        val password = it[1] as String?
        !(username.isNullOrBlank() || password.isNullOrBlank())
    }

    private val _authenticationState = MutableLiveData<AuthenticationState>().apply {
        value = authRepo.findCurrentUser()?.let { AuthenticationState.Authenticated(it) }
            ?: AuthenticationState.Unauthenticated
    }
    val authenticationState: LiveData<AuthenticationState> = _authenticationState

    fun onLoginButtonClick() = viewModelScope.launch {
        _authenticationState.value = withContext(Dispatchers.IO) {
            authRepo.login(username.value!!, password.value!!)
        }.fold(
            { AuthenticationState.Authenticated(it) },
            { AuthenticationState.InvalidAuthentication(it) }
        )
    }

    sealed class AuthenticationState {

        object Unauthenticated : AuthenticationState()

        class Authenticated(val user: AuthenticatedUserModel) : AuthenticationState()

        class InvalidAuthentication(val throwable: Throwable) : AuthenticationState()
    }
}