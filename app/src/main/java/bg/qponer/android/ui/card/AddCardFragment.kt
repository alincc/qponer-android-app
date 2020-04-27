package bg.qponer.android.ui.card

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import bg.qponer.android.R
import bg.qponer.android.data.model.CardRegistrationData
import bg.qponer.android.ui.viewModelsUsingAppFactory
import com.mangopay.android.sdk.Callback
import com.mangopay.android.sdk.MangoPay
import com.mangopay.android.sdk.model.CardRegistration
import com.mangopay.android.sdk.model.MangoCard
import com.mangopay.android.sdk.model.MangoSettings
import com.mangopay.android.sdk.model.exception.MangoException
import kotlinx.android.synthetic.main.fragment_add_card.*

class AddCardFragment : Fragment() {

    private val addCardViewModel by viewModelsUsingAppFactory<AddCardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_add_card, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addCardCardNumberEditText.addTextChangedListener(
            afterTextChanged = { addCardViewModel.number = it.toString() }
        )
        addCardCardExpiryDateEditText.addTextChangedListener(
            afterTextChanged = { addCardViewModel.expiryDate = it.toString() }
        )
        addCardCardCvvEditText.addTextChangedListener(
            afterTextChanged = { addCardViewModel.cvv = it.toString() }
        )

        addCardSaveButton.setOnClickListener { addCardViewModel.onSaveButtonClicked() }

        addCardViewModel.apply {
            isSaveButtonEnabled.observe(viewLifecycleOwner, Observer {
                addCardSaveButton.isEnabled = it
            })
            cardRegistrationData.observe(viewLifecycleOwner, Observer {
                registerCard(it)
            })
        }
    }

    private fun registerCard(data: CardRegistrationData) {
        val settings = MangoSettings(
            data.baseUrl,
            data.clientId,
            data.cardPreregistrationId,
            data.cardRegistrationUrl,
            data.preregistrationData,
            data.accessKey
        )
        val card = MangoCard(
            addCardViewModel.number,
            addCardViewModel.expiryDate,
            addCardViewModel.cvv
        )

        MangoPay(requireContext(), settings).registerCard(card, object: Callback {
            override fun success(jsonResponse: CardRegistration) {
                addCardViewModel.onCardPreregistrationComplete(jsonResponse)
            }

            override fun failure(error: MangoException?) {
                Log.e("DAKA", "Error while registering card: $error")
            }
        })
    }
}