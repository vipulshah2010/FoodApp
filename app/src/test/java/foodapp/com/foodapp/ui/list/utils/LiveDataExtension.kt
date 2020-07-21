package foodapp.com.foodapp.ui.list.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeForTesting(block: (ValuesObserver<T>) -> Unit) {
    val observer = ValuesObserver<T>()
    try {
        observeForever(observer)
        block(observer)
    } finally {
        removeObserver(observer)
    }
}

class ValuesObserver<T> : Observer<T> {
    val values = mutableListOf<T>()

    override fun onChanged(t: T) {
        values.add(t)
    }
}