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

        databaseQuery = { dao.loadEpisodes(showId, seasonId) },
        networkCall = { service.getEpisodes(showId, seasonId) },
        saveCallResult = {
            //TODO: Once the backend implements the seasonId into the json, remove this assignation and set seasonId as "val"
            val episodes = it.body()!!

            episodes.forEach { episode ->
                episode.seasonId = seasonId
            }

            dao.insertAll(episodes)
        }
    )
}