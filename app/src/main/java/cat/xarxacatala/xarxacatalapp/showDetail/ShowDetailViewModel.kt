package cat.xarxacatala.xarxacatalapp.showDetail

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import cat.xarxacatalapp.core.CallResult
import cat.xarxacatalapp.core.ResettableLazyManager
import cat.xarxacatalapp.core.models.Episode
import cat.xarxacatalapp.core.repository.EpisodesRepository
import cat.xarxacatalapp.core.repository.PlaylistsRepository
import cat.xarxacatalapp.core.repository.ShowsRepository
import cat.xarxacatalapp.core.resettableLazy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@HiltViewModel
class ShowDetailViewModel @Inject constructor(
    val showsRepository: ShowsRepository,
    val playlistRepository: PlaylistsRepository,
    val episodesRepository: EpisodesRepository
) : ViewModel() {
    var showId = 0
    var playlistId = 0
        set(value) {
            field = value
            lazyManager.reset()
        }
    val lazyManager = ResettableLazyManager()

    val show by lazy {
        showsRepository.show(showId).asLiveData()
    }

    val playlists by lazy {
        playlistRepository.playlists(showId).asLiveData()
    }

    val episodes: LiveData<CallResult<List<Episode>>> by resettableLazy(
        lazyManager
    ) {
        episodesRepository.episodes(showId, playlistId).asLiveData()
    }
}
