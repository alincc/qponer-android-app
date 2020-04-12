package bg.qponer.android.ui.business

import android.util.Log
import androidx.lifecycle.*
import bg.qponer.android.data.model.Business
import bg.qponer.android.data.repository.BusinessOwnerRepository
import bg.qponer.android.util.Result
import kotlinx.coroutines.Dispatchers

class BusinessSharedViewModel(
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

    private val _selectedItem = MutableLiveData<Business>()
    var selectedItem: LiveData<Business> = _selectedItem

    fun select(item: Business) {
        _selectedItem.value = item
    }
}