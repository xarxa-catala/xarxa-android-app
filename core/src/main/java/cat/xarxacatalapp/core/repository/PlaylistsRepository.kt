package cat.xarxacatalapp.core.repository

import cat.xarxacatalapp.core.db.dao.PlaylistDao
import cat.xarxacatalapp.core.network.XarxaCatalaService
import cat.xarxacatalapp.core.resultFlow
import javax.inject.Inject


class PlaylistsRepository @Inject constructor(
    private val dao: PlaylistDao,
    private val service: XarxaCatalaService
) {
    fun playlists(showId: Int) = resultFlow(
        databaseQuery = { dao.loadPlaylists(showId) },
        networkCall = { service.getPlaylists(showId) },
        saveCallResult = { dao.insertAll(it.body()!!) }
    )
}