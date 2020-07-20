package cat.xarxacatala.xarxacatalapp.videoPlayer

import cat.xarxacatala.xarxacatalapp.cast.CastBaseViewModel
import cat.xarxacatala.xarxacatalapp.cast.CastManager
import cat.xarxacatalapp.core.repository.EpisodesRepository
import javax.inject.Inject

class VideoPlayerViewModel @Inject constructor(
    episodesRepository: EpisodesRepository,
    castManager: CastManager
) : CastBaseViewModel(castManager) {
    var episodeId = 0

    val episode by lazy { episodesRepository.episode(episodeId) }
}