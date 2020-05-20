package cat.xarxacatalapp.core.repository

import cat.xarxacatalapp.core.db.dao.EpisodeDao
import cat.xarxacatalapp.core.network.XarxaCatalaService
import cat.xarxacatalapp.core.resultLiveData
import javax.inject.Inject

class EpisodesRepository @Inject constructor(
    private val dao: EpisodeDao,
    private val service: XarxaCatalaService
) {
    fun episodes(showId: Int, seasonId: Int) = resultLiveData(
        databaseQuery = { dao.loadEpisodes(showId) },
        networkCall = { service.getEpisodes(showId, seasonId) },
        saveCallResult = { dao.insertAll(it.body()!!) }
    )
}