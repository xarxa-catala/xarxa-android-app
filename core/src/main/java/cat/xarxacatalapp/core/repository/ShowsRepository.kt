package cat.xarxacatalapp.core.repository

import cat.xarxacatalapp.core.db.dao.ShowDao
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
        saveCallResult = { dao.insertAll(it.body()!!) }
    )
}