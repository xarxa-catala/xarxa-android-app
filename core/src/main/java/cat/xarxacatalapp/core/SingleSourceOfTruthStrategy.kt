package cat.xarxacatalapp.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [CallResult.Status.SUCCESS] - with data from database
 * [CallResult.Status.ERROR] - if error has occurred from any source
 * [CallResult.Status.LOADING]
 */
fun <T, A> resultLiveData(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> A,
    saveCallResult: suspend (A) -> Unit
): LiveData<CallResult<T>> =
    liveData(Dispatchers.IO) {
        emit(CallResult.loading())
        val source = databaseQuery.invoke().map { CallResult.success(it) }
        emitSource(source)

        try {
            val responseStatus = networkCall.invoke()
            saveCallResult(responseStatus)
        } catch (ex: Exception) {
            emit(CallResult.error(ex.message!!))
            emitSource(source)
        }
    }