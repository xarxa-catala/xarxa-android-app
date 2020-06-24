package cat.xarxacatala.xarxacatalapp.videoPlayer

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import cat.xarxacatala.xarxacatalapp.BaseFragment
import cat.xarxacatala.xarxacatalapp.MainActivity
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatala.xarxacatalapp.XarxaCatApp
import cat.xarxacatala.xarxacatalapp.di.injectViewModel
import cat.xarxacatalapp.core.CallResult
import cat.xarxacatalapp.core.models.Episode
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.exo_playback_control_view.*
import kotlinx.android.synthetic.main.fragment_video_player.*
import javax.inject.Inject


class VideoPlayerFragment : BaseFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: VideoPlayerViewModel
    private lateinit var player: SimpleExoPlayer

    val args: VideoPlayerFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity?.application as XarxaCatApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = injectViewModel(viewModelFactory)
        viewModel.episodeId = args.episodeId

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            setupPlayer(it)
        }
    }

    private fun setupPlayer(context: Context) {
        val trackSelector = DefaultTrackSelector(context)
        trackSelector.setParameters(
            trackSelector
                .buildUponParameters()
                .setPreferredAudioLanguage("ca")
        )

        player = SimpleExoPlayer.Builder(requireContext())
            .setTrackSelector(trackSelector)
            .build()

        player.addListener(PlayerEventListener(playerView))

        // Attach player to the view.
        playerView.player = player
    }

    private fun subscribeUi() {
        viewModel.episode.observe(viewLifecycleOwner, Observer {
            if (it.status == CallResult.Status.SUCCESS) {

                loadEpisode(it.data!!)
            }
        })
    }

    private fun loadEpisode(episode: Episode) {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(requireContext(), "XarxaCatalApp")
        )

        tvTitle.text = episode.name

        val url = episode.url

        Log.e("ABDE", "The media URL is $url")

        // This is the MediaSource representing the media to be played.

        val videoSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse(url))

        // Prepare the player with the source.
        videoSource?.let {
            player.prepare(it, false, true)
            player.playWhenReady = true
        }

    }

    override fun onResume() {
        super.onResume()

        subscribeUi()
    }

    override fun onPause() {
        super.onPause()

        player.stop()
    }

    private inner class PlayerEventListener(val playerView: PlayerView) : Player.EventListener {
        override fun onPlayerStateChanged(
            playWhenReady: Boolean,
            playbackState: Int
        ) {
            playerView.keepScreenOn =
                !(playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED ||
                        !playWhenReady)

            (activity as MainActivity).fullScreen()
        }
    }
}
