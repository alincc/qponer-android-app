package bg.qponer.android.ui.businesses

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import bg.qponer.android.data.repository.BusinessOwnerRepository
import bg.qponer.android.util.Result
import kotlinx.coroutines.Dispatchers

class BusinessesViewModel(
    private val businessOwnerRepo: BusinessOwnerRepository
) : ViewModel() {

    private val _filter = MutableLiveData<String>()
    var filter: String?
        get() = _filter.value
        set(value) {
            _filter.value = value
        }

    val businessOwners = Transformations.switchMap(_filter) {
        liveData(Dispatchers.IO) {
            when (val result = businessOwnerRepo.findBusinessOwners()) {
                is Result.Success -> emit(result.payload)
                is Result.Failure -> Log.e(
                    "DAKA",
                    "Error while loading business owners",
                    result.throwable
                )
            }
        }
    }
}