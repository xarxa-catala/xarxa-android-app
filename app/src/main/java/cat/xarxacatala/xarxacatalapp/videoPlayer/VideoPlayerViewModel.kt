package cat.xarxacatala.xarxacatalapp.videoPlayer

import androidx.lifecycle.ViewModel
import cat.xarxacatalapp.core.repository.EpisodesRepository
import javax.inject.Inject

class VideoPlayerViewModel @Inject constructor(episodesRepository: EpisodesRepository) :
    ViewModel() {
    var episodeId = 0

    val episode by lazy { episodesRepository.episode(episodeId) }
}