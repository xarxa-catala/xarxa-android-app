package cat.xarxacatalapp.core.repository

import cat.xarxacatalapp.core.db.dao.SeasonDao
import cat.xarxacatalapp.core.network.XarxaCatalaService
import cat.xarxacatalapp.core.resultLiveData
import javax.inject.Inject


class SeasonsRepository @Inject constructor(
    private val dao: SeasonDao,
    private val service: XarxaCatalaService
) {
    fun seasons(showId: Int) = resultLiveData(
        databaseQuery = { dao.loadSeasons(showId) },
        networkCall = { service.getSeasons(showId) },
        saveCallResult = { dao.insertAll(it.body()!!) }
    )
}