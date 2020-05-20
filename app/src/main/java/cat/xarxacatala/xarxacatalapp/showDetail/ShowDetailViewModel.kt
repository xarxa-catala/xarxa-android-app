package cat.xarxacatala.xarxacatalapp.showDetail

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import cat.xarxacatalapp.ResettableLazyManager
import cat.xarxacatalapp.core.CallResult
import cat.xarxacatalapp.core.models.Episode
import cat.xarxacatalapp.core.repository.EpisodesRepository
import cat.xarxacatalapp.core.repository.SeasonsRepository
import cat.xarxacatalapp.core.repository.ShowsRepository
import cat.xarxacatalapp.resettableLazy
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ShowDetailViewModel @Inject constructor(
    val showsRepository: ShowsRepository,
    val seasonRepository: SeasonsRepository,
    val episodesRepository: EpisodesRepository
) : ViewModel() {
    var showId = 0
    var seasonId = 0
        set(value) {
            field = value
            lazyManager.reset()
        }
    val lazyManager = ResettableLazyManager()

    private var seasonIdChanged = true

    val show by lazy {
        showsRepository.show(showId)
    }

    val seasons by lazy {
        seasonRepository.seasons(showId)
    }

    val episodes: LiveData<CallResult<List<Episode>>> by resettableLazy(lazyManager) {
        episodesRepository.episodes(showId, seasonId)
    }
}
