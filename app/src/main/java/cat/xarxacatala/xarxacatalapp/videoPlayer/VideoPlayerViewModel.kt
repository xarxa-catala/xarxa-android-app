package cat.xarxacatala.xarxacatalapp.videoPlayer

import androidx.lifecycle.asLiveData
import cat.xarxacatala.xarxacatalapp.cast.CastBaseViewModel
import cat.xarxacatala.xarxacatalapp.cast.CastManager
import cat.xarxacatalapp.core.repository.EpisodesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    episodesRepository: EpisodesRepository,
    castManager: CastManager
) : CastBaseViewModel(castManager) {
    var episodeId = 0

    val episode by lazy { episodesRepository.episode(episodeId).asLiveData() }
}