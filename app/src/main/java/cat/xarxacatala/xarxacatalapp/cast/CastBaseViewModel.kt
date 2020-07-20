package cat.xarxacatala.xarxacatalapp.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.exoplayer2.ext.cast.CastPlayer
import com.google.android.gms.cast.MediaQueueItem

abstract class CastBaseViewModel(
    private val castManager: CastManager
) : ViewModel() {
    fun loadCastMedia(mediaItems: Array<MediaQueueItem>) {
        castManager.loadMedia(mediaItems)
    }

    val progressLiveData = castManager.progressLiveData
    val castPlayerLiveData: LiveData<CastPlayer?> = castManager.castPlayerLiveData
    val castState: LiveData<Int> = castManager.castState
}