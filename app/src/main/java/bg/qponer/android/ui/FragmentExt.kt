package bg.qponer.android.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import bg.qponer.android.QponerApp

inline fun <reified T : ViewModel> Fragment.activityViewModelsUsingAppFactory() =
    activityViewModels<T> {
        (requireContext().applicationContext as QponerApp).viewModelFactory
    }

inline fun <reified T : ViewModel> Fragment.viewModelsUsingAppFactory() =
    viewModels<T> {
        (requireContext().applicationContext as QponerApp).viewModelFactory
    }