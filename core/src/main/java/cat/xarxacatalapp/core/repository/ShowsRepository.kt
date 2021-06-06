package cat.xarxacatalapp.core.repository

import cat.xarxacatalapp.core.CallResult
import cat.xarxacatalapp.core.db.dao.ShowDao
import cat.xarxacatalapp.core.models.Show
import cat.xarxacatalapp.core.network.XarxaCatalaService
import cat.xarxacatalapp.core.resultFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ShowsRepository @Inject constructor(
    private val dao: ShowDao,
    private val service: XarxaCatalaService
) {
    val shows = resultFlow(
        databaseQuery = { dao.loadAllShows() },
        networkCall = { service.shows() },
        saveCallResult = {
            dao.insertAll(it.body()!!)
        }
    )

    fun show(showId: Int): Flow<CallResult<Show>> = resultFlow(
        databaseQuery = { dao.loadShow(showId) },
        networkCall = { null },
        saveCallResult = { }
    )
}