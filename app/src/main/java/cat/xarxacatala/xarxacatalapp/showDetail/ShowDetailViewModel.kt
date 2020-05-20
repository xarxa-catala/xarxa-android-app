package cat.xarxacatala.xarxacatalapp.showDetail

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import cat.xarxacatalapp.core.repository.EpisodesRepository
import cat.xarxacatalapp.core.repository.SeasonsRepository
import cat.xarxacatalapp.core.repository.ShowsRepository
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class ShowDetailViewModel @Inject constructor(
    showsRepository: ShowsRepository,
    seasonRepository: SeasonsRepository,
    episodesRepository: EpisodesRepository
) : ViewModel() {
    var showId = 0
    var seasonId = 0

    val show by lazy {
        showsRepository.show(showId)
    }

    val seasons by lazy {
        seasonRepository.seasons(showId)
    }

    val episodes by lazy {
        episodesRepository.episodes(showId, seasonId)
    }
}
