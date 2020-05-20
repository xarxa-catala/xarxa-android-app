package cat.xarxacatalapp.core.repository

import androidx.lifecycle.LiveData
import cat.xarxacatalapp.core.CallResult
import cat.xarxacatalapp.core.db.dao.ShowDao
import cat.xarxacatalapp.core.models.Show
import cat.xarxacatalapp.core.network.XarxaCatalaService
import cat.xarxacatalapp.core.resultLiveData
import javax.inject.Inject


class ShowsRepository @Inject constructor(
    private val dao: ShowDao,
    private val service: XarxaCatalaService
) {
    val shows = resultLiveData(
        databaseQuery = { dao.loadAllShows() },
        networkCall = { service.shows() },
        saveCallResult = {
            dao.insertAll(it.body()!!)
        }
    )

    fun show(showId: Int): LiveData<CallResult<Show>> = resultLiveData(
        databaseQuery = { dao.loadShow(showId) },
        networkCall = { null },
        saveCallResult = { }
    )
}