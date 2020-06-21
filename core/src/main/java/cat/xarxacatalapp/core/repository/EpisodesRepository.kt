package cat.xarxacatalapp.core.repository

import cat.xarxacatalapp.core.db.dao.EpisodeDao
import cat.xarxacatalapp.core.network.XarxaCatalaService
import cat.xarxacatalapp.core.resultLiveData
import javax.inject.Inject

class EpisodesRepository @Inject constructor(
    private val dao: EpisodeDao,
    private val service: XarxaCatalaService
) {
    fun episodes(showId: Int, playlistId: Int) = resultLiveData(
        databaseQuery = { dao.loadEpisodes(showId, playlistId) },
        networkCall = { service.getEpisodes(showId, playlistId) },
        saveCallResult = {
            val episodes = it.body()

            if(episodes != null) {
                episodes.forEach { it.playlistId = playlistId }

                dao.insertAll(episodes)
            }
        }
    )

    fun episode(episodeId: Int)  = resultLiveData(
        databaseQuery = { dao.loadEpisode(episodeId) },
        networkCall = { null },
        saveCallResult = { }
    )
}