package cat.xarxacatalapp.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [CallResult.Status.SUCCESS] - with data from database
 * [CallResult.Status.ERROR] - if error has occurred from any source
 * [CallResult.Status.LOADING]
 */
@ExperimentalCoroutinesApi
fun <T, A> resultFlow(
    databaseQuery: () -> Flow<T>,
    networkCall: suspend () -> A,
    saveCallResult: suspend (A) -> Unit
): Flow<CallResult<T>> = flow {
        val source = databaseQuery.invoke().map { CallResult.success(it) }
        source.onStart {
            emit(CallResult.loading())
        }

        withContext(Dispatchers.IO) {
            async {
                try {
                    val responseStatus = networkCall.invoke()
                    saveCallResult(responseStatus)
                } catch (ex: Exception) {
                    ex.printStackTrace()
//                    emit(CallResult.error(ex.message.toString(), null))
                }
            }
        }

        emitAll(source)
    }