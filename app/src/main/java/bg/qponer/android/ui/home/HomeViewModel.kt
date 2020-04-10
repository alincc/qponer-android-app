package bg.qponer.android.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import bg.qponer.android.data.repository.ContributorRepository
import bg.qponer.android.util.Result
import kotlinx.coroutines.Dispatchers

class HomeViewModel(
    private val contributorRepo: ContributorRepository
) : ViewModel() {

    private val _userId = MutableLiveData<Long>()
    var userId: Long?
        get() = _userId.value
        set(value) {
            _userId.value = value
        }

    val vouchers = Transformations.switchMap(_userId) {
        liveData(Dispatchers.IO) {
            when (val result = contributorRepo.findAllVouchers(it)) {
                is Result.Success -> emit(result.payload)
                is Result.Failure -> Log.e("DAKA", "Could not load vouchers", result.throwable)
            }
        }
    }

}