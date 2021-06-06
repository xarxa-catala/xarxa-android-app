package cat.xarxacatala.xarxacatalapp.cast

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cat.xarxacatalapp.core.extensions.default
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ext.cast.CastPlayer
import com.google.android.exoplayer2.ext.cast.SessionAvailabilityListener
import com.google.android.gms.cast.MediaQueueItem
import com.google.android.gms.cast.framework.CastContext
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


const val KEY_EPISODE_ID = "cat.xaracatala.xatacatalapp.EPISODE_ID"
/**
 * Class used to manage CastPlayer state. This wraps the required data around liveData to observe easily changes from the UI
 */
class CastManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val castContext: CastContext
) {
    val castPlayerLiveData = MutableLiveData<CastPlayer?>()
    val progressLiveData = MutableLiveData<Int>().default(-1)

    private val _castState = MutableLiveData<Int>()
    val castState: LiveData<Int>
        get() = _castState

    private val castEventListener by lazy { CastEventLister() }

    init {
        castContext.addCastStateListener { state ->
            _castState.value = state
        }

        val castPlayer = CastPlayer(castContext)

        castPlayer.setSessionAvailabilityListener(object : SessionAvailabilityListener {
            override fun onCastSessionAvailable() {
                castPlayerLiveData.value = castPlayer
                castPlayer.addListener(castEventListener)

            }

            override fun onCastSessionUnavailable() {
                castPlayerLiveData.value = null
                castPlayer.release()
            }
        })
    }

    private var handler = Handler()
    private var runnable: Runnable? = null

    fun loadMedia(mediaItems: Array<MediaQueueItem>) {

        try {
            castPlayerLiveData.value?.release()
        } catch (ex: Exception) {
            Log.e("ABDE", "Release crashed.")
        }

        castPlayerLiveData.value = null

        val castPlayer = CastPlayer(castContext)

        castPlayer.setSessionAvailabilityListener(object : SessionAvailabilityListener {
            override fun onCastSessionAvailable() {
                castPlayer.loadItems(mediaItems, 0, 0, Player.REPEAT_MODE_OFF)
                castPlayerLiveData.value = castPlayer
                castPlayer.addListener(castEventListener)
            }

            override fun onCastSessionUnavailable() {
                castPlayerLiveData.value = null
                castPlayer.release()
            }
        })
    }

    private inner class CastEventLister : Player.EventListener {
        override fun onPlayerStateChanged(
            playWhenReady: Boolean,
            playbackState: Int
        ) {
            if (playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED || !playWhenReady) {
                runnable?.let {
                    handler.removeCallbacks(it)
                }

                if (playbackState == Player.STATE_ENDED)
                    castPlayerLiveData.value = null

            } else {
                if (runnable == null) {
                    runnable = Runnable {
                        castPlayerLiveData.value?.let { castPlayer ->
                            val progress = (castPlayer.currentPosition * 1000) / castPlayer.duration
                            progressLiveData.value = progress.toInt()
                            runnable?.let { handler.postDelayed(it, 1000) }
                        }
                    }
                }

                runnable?.let {
                    handler.postDelayed(it, 0)
                }
            }
        }
    }
}