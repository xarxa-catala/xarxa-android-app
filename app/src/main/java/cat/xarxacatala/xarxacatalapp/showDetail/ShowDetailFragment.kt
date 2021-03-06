package cat.xarxacatala.xarxacatalapp.showDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import cat.xarxacatala.xarxacatalapp.BaseFragment
import cat.xarxacatala.xarxacatalapp.R
import cat.xarxacatala.xarxacatalapp.utils.setToolbarTitle
import cat.xarxacatalapp.core.CallResult
import cat.xarxacatalapp.core.models.Episode
import cat.xarxacatalapp.core.models.Playlist
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_show_detail.*
import kotlinx.android.synthetic.main.fragment_show_detail.*

@AndroidEntryPoint
class ShowDetailFragment : BaseFragment() {
    private val viewModel: ShowDetailViewModel by viewModels()

    private var epsObserver: Observer<CallResult<List<Episode>>>? = null

    val args: ShowDetailFragmentArgs by navArgs()

    private lateinit var episodesAdapter: EpisodesListAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.showId = args.showId

        return inflater.inflate(R.layout.fragment_show_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as MainActivity).fullScreen()

        context?.let { context ->
            episodesAdapter = EpisodesListAdapter(context)
            rvEpisodes.layoutManager = LinearLayoutManager(requireContext())
            rvEpisodes.adapter = episodesAdapter
        }

        subscribeUi()

        val navHostFragment = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(toolbar_layout, toolbar, navHostFragment)
    }

    private fun subscribeUi() {
        viewModel.show.observe(viewLifecycleOwner, Observer { result ->
            if (result.status == CallResult.Status.SUCCESS) {
                result.data?.let {
                    setToolbarTitle(it.name)

                    Glide.with(this)
                        .load(it.thumbnail)
                        .into(ivShowThumbnail)
                }
            }
        })

        viewModel.playlists.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                CallResult.Status.SUCCESS -> {
                    result.data?.let { playlists ->

                        val adapter = PlaylistsAdapter(
                            requireContext(),
                            playlists
                        )

                        tvPlaylists.setAdapter(adapter)


                        if (viewModel.playlistId != 0) {
                            // Select the previous item after refreshing data or returning from a previous screen
                            playlists.forEach {
                                if (it.id == viewModel.playlistId) {
                                    loadEpisodes(it)
                                }
                            }
                        } else if (playlists.count() > 0) {
                            // Load the first item if nothing has been selected previously
                            val firstPlaylist = playlists.first()
                            tvPlaylists.setText(firstPlaylist.name, false)
                            loadEpisodes(firstPlaylist)
                        }

                    }
                }

                CallResult.Status.ERROR -> {
                    Snackbar.make(rootView, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })

        tvPlaylists.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                loadEpisodes(
                    tvPlaylists.adapter.getItem(
                        position
                    ) as Playlist
                )
            }
    }

    private fun loadEpisodes(playlist: Playlist) {

        // After changing the selected playlist, we don't want to keep observing the changes to
        // the episodes of the previous playlist selected
        epsObserver?.let {
            viewModel.episodes.removeObserver(it)
        }

        viewModel.playlistId = playlist.id

        epsObserver = Observer<CallResult<List<Episode>>> { result ->
            when (result.status) {
                CallResult.Status.SUCCESS -> {
                    result.data?.let { episodes ->
                        episodesAdapter.submitList(episodes)
                    }
                }
                CallResult.Status.ERROR -> {
                    Snackbar.make(rootView, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        }

        viewModel.episodes.observe(viewLifecycleOwner, epsObserver!!)
    }
}
