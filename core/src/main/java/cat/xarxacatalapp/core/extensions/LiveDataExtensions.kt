package cat.xarxacatalapp.core.extensions

import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.default(value: T): MutableLiveData<T> {
    this.value = value
    return this
}