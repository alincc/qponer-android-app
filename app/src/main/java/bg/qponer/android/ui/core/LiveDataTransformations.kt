package bg.qponer.android.ui.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

class LiveDataTransformations {

    companion object {

        fun <R> combineLatest(
            vararg sources: LiveData<out Any>,
            combinator: (List<Any?>) -> R
        ): LiveData<R> =
            CombineLatestLiveData(sources, combinator)

    }

}

private class CombineLatestLiveData<T>(
    sources: Array<out LiveData<out Any>>,
    combinator: (List<Any?>) -> T
) : MediatorLiveData<T>() {

    private val combineLatestObserver = Observer<Any> {
        val values = sources.map { it.value }.toList()
        value = combinator(values)
    }

    init {
        sources.forEach { addSource(it, combineLatestObserver) }
    }

}