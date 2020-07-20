package cat.xarxacatala.xarxacatalapp.cast

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import cat.xarxacatala.xarxacatalapp.BaseFragment
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatala.xarxacatalapp.XarxaCatApp
import cat.xarxacatala.xarxacatalapp.di.injectViewModel
import cat.xarxacatala.xarxacatalapp.extensions.forward
import cat.xarxacatala.xarxacatalapp.extensions.pause
import cat.xarxacatala.xarxacatalapp.extensions.play
import cat.xarxacatala.xarxacatalapp.extensions.replay
import com.google.android.exoplayer2.ext.cast.CastPlayer
import com.google.android.gms.cast.MediaInfo
import kotlinx.android.synthetic.main.fragment_cast.*
import javax.inject.Inject

class CastFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CastViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application as XarxaCatApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = injectViewModel(viewModelFactory)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.progressLiveData.observe(viewLifecycleOwner, Observer { progress ->
            sbProgress.progress = progress
        })

        viewModel.castPlayerLiveData.observe(viewLifecycleOwner, Observer { castPlayer ->
            if (castPlayer == null) {
                view.findNavController().navigateUp()
            } else {
                var mediaInfo: MediaInfo? = null
                try {
                    mediaInfo = castPlayer.getItem(1)?.media
                } catch (ex: Exception) {
                    Log.e("ABDE", "Failed to load media info from cast")
                }

                if (mediaInfo != null)
                    loadEpisode(mediaInfo)

                setupEvents(castPlayer)
            }
        })
    }

    private fun loadEpisode(mediaInfo: MediaInfo) {
        val metaData = mediaInfo.metadata
    }

    private fun setupEvents(castPlayer: CastPlayer) {
        btnPlay.setOnClickListener {
            castPlayer.play()
            btnPause.visibility = VISIBLE
            btnPlay.visibility = GONE
        }

        btnPause.setOnClickListener {
            castPlayer.pause()
            btnPause.visibility = GONE
            btnPlay.visibility = VISIBLE
        }

        btnReplay.setOnClickListener { castPlayer.replay() }
        btnForward.setOnClickListener { castPlayer.forward() }

        btnStop.setOnClickListener { castPlayer.stop() }
    }
}