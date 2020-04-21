package bg.qponer.android.ui.card

import android.util.Log
import androidx.lifecycle.*
import bg.qponer.android.data.model.CardRegistrationData
import bg.qponer.android.data.repository.CardRepository
import bg.qponer.android.util.onSuccess
import com.mangopay.android.sdk.model.CardRegistration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCardViewModel(
    private val cardRepo: CardRepository
) : ViewModel() {

    private val validationObserver = Observer<String> {
        val number = _number.value
        val expiryDate = _expiryDate.value
        val cvv = _cvv.value

        validateInput(number, expiryDate, cvv)
    }

    private val _number = MutableLiveData<String>()
    var number: String?
        get() = _number.value
        set(value) {
            _number.value = value
        }

    private val _expiryDate = MutableLiveData<String>()
    var expiryDate: String?
        get() = _expiryDate.value
        set(value) {
            _expiryDate.value = value
        }

    private val _cvv = MutableLiveData<String>()
    var cvv: String?
        get() = _cvv.value
        set(value) {
            _cvv.value = value
        }

    private val _isSaveButtonEnabled = MediatorLiveData<Boolean>().apply {
        addSource(_number, validationObserver)
        addSource(_expiryDate, validationObserver)
        addSource(_cvv, validationObserver)
    }
    val isSaveButtonEnabled: LiveData<Boolean> = _isSaveButtonEnabled

    private val _cardRegistrationData = MutableLiveData<CardRegistrationData>()
    val cardRegistrationData: LiveData<CardRegistrationData> = _cardRegistrationData

    fun onSaveButtonClicked() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { cardRepo.startRegistration() }
                .onSuccess { _cardRegistrationData.value = it }
        }
    }

    fun onCardPreregistrationComplete(cardRegistration: CardRegistration) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cardRepo.finishRegistration(
                    cardRegistration.id.toLong(),
                    cardRegistration.registrationData,
                    _number.value!!,
                    _expiryDate.value!!
                )
            }
                .onSuccess { Log.d("DAKA", "Card registered: $it") }
        }
    }

    private fun validateInput(number: String?, expiryDate: String?, cvv: String?) {
        _isSaveButtonEnabled.value = number?.let { it.length in 16..19 } ?: false
                && expiryDate?.let { it.length == 4 } ?: false
                && cvv?.let { it.length == 3 } ?: false
    }
}