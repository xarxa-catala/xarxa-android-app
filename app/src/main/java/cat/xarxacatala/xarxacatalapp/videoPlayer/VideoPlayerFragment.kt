package cat.xarxacatala.xarxacatalapp.videoPlayer

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import cat.xarxacatala.xarxacatalapp.BaseFragment
import cat.xarxacatala.xarxacatalapp.MainActivity
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatala.xarxacatalapp.XarxaCatApp
import cat.xarxacatala.xarxacatalapp.di.injectViewModel
import cat.xarxacatalapp.core.CallResult
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
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


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onResume() {
        super.onResume()

        val a = activity as MainActivity?
        if (a != null) {
            a.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            a.fullScreen()
        }
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

        subscribeUi()

        player = SimpleExoPlayer.Builder(requireContext()).build();

        // Attach player to the view.
        playerView.player = player;
        // Prepare the player with the media source.

    }

    private fun subscribeUi() {
        viewModel.episode.observe(viewLifecycleOwner, Observer {
            if (it.status == CallResult.Status.SUCCESS) {
                // Produces DataSource instances through which media data is loaded.
                val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                    context,
                    Util.getUserAgent(requireContext(), "XarxaCatalApp")
                )

                val url = it.data!!.url

                Log.e("ABDE", "The media URL is $url")

                // This is the MediaSource representing the media to be played.
                val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(url))

                // Prepare the player with the source.
                player.prepare(videoSource)
            }
        })
    }

    override fun onStop() {
        super.onStop()

        player.stop()
    }
}
